package com.doer.edgeservice.Service;

import com.alibaba.fastjson.JSONObject;
import com.doer.edgeservice.Entities.Cart;
import com.doer.edgeservice.Entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public String handleFileUpload(MultipartFile file) throws IOException {
        JSONObject jsonObject = new JSONObject();
        if (file.isEmpty()){
            jsonObject.put("status","fail");
        }else {
            byte[] bytes = file.getBytes();
            Resource resource = new ClassPathResource("static/img");
            String fileName = String.valueOf(System.currentTimeMillis());
            String pathPrefix = resource.getFile().getPath()+"/";
            String filePrefix = "/"+resource.getFilename()+"/";
            String filePostfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            File fileToSave = new File(pathPrefix+fileName+filePostfix);
            FileCopyUtils.copy(bytes, fileToSave);
            jsonObject.put("status","success");
            jsonObject.put("path",filePrefix+fileName+filePostfix);
        }
        return jsonObject.toJSONString();
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
}

/*
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<String>(order.toString(), headers);
        ResponseEntity entity = restTemplate.postForEntity(uri,request,String.class);
 */