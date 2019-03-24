package com.doer.edgeservice.Service;

import com.alibaba.fastjson.JSONObject;
import com.doer.edgeservice.Entities.Cart;
import com.doer.edgeservice.Entities.CartProductBundle;
import com.doer.edgeservice.Entities.Order;
import com.doer.edgeservice.Entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EdgeService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentService paymentService;

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    public String handleFileUpload(MultipartFile file) throws IOException {
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()){
            jsonObject.put("status","fail");
        }else {
            byte[] bytes = file.getBytes();
            String fileName = String.valueOf(System.currentTimeMillis());
            String pathPrefix = uploadFolder;
            String filePrefix = staticAccessPath.substring(0,staticAccessPath.length()-2);
            String filePostfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            File folder = new File(uploadFolder);
            if (!folder.exists()){
                folder.mkdirs();
            }
            File fileToSave = new File(pathPrefix+fileName+filePostfix);
            if (!fileToSave.exists()){
                fileToSave.createNewFile();
            }
            FileCopyUtils.copy(bytes, fileToSave);
            jsonObject.put("status","success");
            jsonObject.put("path",filePrefix+fileName+filePostfix);
        }
        return jsonObject.toJSONString();
    }

    public boolean deleteFile(String imgSrc) throws IOException {
        String pathPrefix = uploadFolder;
        String filePrefix = staticAccessPath.substring(0,staticAccessPath.length()-2);
        imgSrc = imgSrc.replaceAll(filePrefix,"");
        File file = new File(pathPrefix+imgSrc);
        boolean flag = file.delete();
        return flag;
    }

    public boolean buyAllInCart(){
        String user_id = "buyer";
        String resp = cartService.getCartList(0,1000);
        List<Cart> carts = JSON.parseArray(resp,Cart.class);
        if (carts == null)
            return false;
        int total = 0;
        for (Cart item : carts){
            boolean addOrderFlag = addOrderItem(item);
            boolean increaseClosingFlag = increaseClosingNum(item.getProductId());
            boolean removeFlag = removeCartItem(item);
            total += item.getCount() * item.getPriceInCart();
        }
        boolean paidFlag = payment(user_id,total);
        return paidFlag;
    }

    private boolean payment(String user_id,int total){
        String resp = paymentService.payment(user_id,total);
        return JSON.parseObject(resp).getBoolean("status");
    }

    private boolean removeCartItem(Cart cart){
        String resp = cartService.decrease(cart.getProductId(),cart.getCount());
        return JSON.parseObject(resp).getBoolean("status");
    }

    private boolean addOrderItem(Cart cart){
        Order order = new Order();
        order.setProductId(cart.getProductId());
        order.setPriceDone(cart.getPriceInCart());
        order.setCount(cart.getCount());
        order.setDate(new Date());
        String resp = orderService.addOrder(order);
        return JSON.parseObject(resp).getBoolean("status");
    }

    private boolean increaseClosingNum(String productId){
        String resp = productService.addClosingNum(productId);
        return JSON.parseObject(resp).getBoolean("status");
    }

    public List<CartProductBundle> getCart(){
        String cartMes = cartService.getCartList(0,1000);
        List<Cart> cartList = JSON.parseArray(cartMes,Cart.class);
        List<CartProductBundle> bundleList = new ArrayList<>();
        for (Cart item :cartList){
            String productId = item.getProductId();
            String productJson = productService.getProduct(productId);
            Product product = JSON.parseObject(productJson,Product.class);
            CartProductBundle bundle = new CartProductBundle(item,product);
            bundleList.add(bundle);
        }
        return bundleList;
    }

    public int getCartSummary(){
        List<Cart> carts = JSON.parseArray(cartService.getCartList(0,1000),Cart.class);
        int summary = 0;
        for (Cart item : carts){
            summary += item.getCount() * item.getPriceInCart();
        }
        return summary;
    }
}