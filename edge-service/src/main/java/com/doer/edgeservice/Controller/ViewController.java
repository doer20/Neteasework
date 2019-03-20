package com.doer.edgeservice.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doer.edgeservice.Entities.Cart;
import com.doer.edgeservice.Entities.Order;
import com.doer.edgeservice.Entities.Product;
import com.doer.edgeservice.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EdgeService edgeService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String getToken(HttpSession httpSession,HttpServletResponse response, String username, String password){
        String token = loginService.getToken(username,password);
        httpSession.setAttribute("Authorization","Bearer "+token);
        String userInfoJson = loginService.authUser();
        JSONObject info = JSON.parseObject(userInfoJson);
        if (info.getString("error")!=null){
            return "fail";
        }else {
            JSONObject details = info.getJSONObject("details");
            JSONObject principal = info.getJSONObject("principal");
            String name = principal.getString("username");
            JSONArray authorities = principal.getJSONArray("authorities");
            List<String> auths = new ArrayList<>();
            for (int i = 0;i < authorities.size();i++){
                auths.add(authorities.getJSONObject(i).getString("authority"));
            }
            httpSession.setAttribute("isLogin",true);
            httpSession.setAttribute("username",name);
            httpSession.setAttribute("authorities",auths);
            Cookie tokenCookie = new Cookie("Authorization", "Bearer-"+token);
            response.addCookie(tokenCookie);
//        response.setHeader("Authorization", "Bearer "+token);
            return "success";
        }
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String homePage(Model model,HttpSession httpSession){
        String productMes = productService.getCatalog(0,1000);
        List<Product> productList = JSON.parseArray(productMes,Product.class);
        model.addAttribute("productList",productList);
        List <String> auth;
        if ((auth = (List<String>) httpSession.getAttribute("authorities")) != null && auth.contains("ROLE_BUYER")){
            String unsoldMes = productService.getUnsoldList();
            List<Product> unsoldList = JSON.parseArray(unsoldMes,Product.class);
            model.addAttribute("unsoldList",unsoldList);
        }
        return "main";
    }

    @ResponseBody
    @RequestMapping(value = "deleteUnsold",method = RequestMethod.POST)
    public String deleteUnsold(String productId){
        String msg = productService.deleteUnsoldProduct(productId);
        return msg;
    }

    @RequestMapping(value = "/detail/{productId}",method = RequestMethod.GET)
    public String detailPage(Model model,@PathVariable String productId){
        String productJson = productService.getProduct(productId);
        Product product = JSON.parseObject(productJson,Product.class);
        if (product.getProductId() == null){
            return "redirect:/home";
        }
        String orderJson = orderService.getOrderByProductId(productId);
        List<Order> orderList = JSON.parseArray(orderJson,Order.class);
        if (!orderList.isEmpty()){
            Order order = orderList.get(0);
            model.addAttribute("order",order);
        }
        String cartJson = cartService.getItem(productId);
        Cart cart = JSON.parseObject(cartJson,Cart.class);
        model.addAttribute("product",product);
        model.addAttribute("cart",cart);
        return "detail";
    }

    @ResponseBody
    @RequestMapping(value = "/increase",method = RequestMethod.POST)
    public String increase(String productId){
        String mes = cartService.increase(productId,1);
        return mes;
    }

    @ResponseBody
    @RequestMapping(value = "/decrease",method = RequestMethod.POST)
    public String decrease(String productId){
        String mes = cartService.decrease(productId,1);
        return mes;
    }

    @ResponseBody
    @RequestMapping(value = "/addIntoCart",method = RequestMethod.POST)
    public String addIntoCart(Cart cart){
        String mes = cartService.addToCart(cart);
        return mes;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPage(Model model,String productId){
        Product product = JSON.parseObject(productService.getProduct(productId),Product.class);
        if (product.getProductId() == null)
            return "redirect:/home";
        model.addAttribute("originProduct",product);
        return "edit";
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(String imgType,@RequestParam(value = "file", required = false) MultipartFile file,Product product) throws IOException {
        String msg;
        if ("url".equals(imgType)){
            msg = productService.updateProduct(product);
        }else{
            String srcJson = edgeService.handleFileUpload(file);
            String status = JSON.parseObject(srcJson).getString("status");
            if ("success".equals(status)){
                String path = JSON.parseObject(srcJson).getString("path");
                product.setImageSrc(path);
                msg = productService.updateProduct(product);
            }else {
                msg = "{\"status\":\"fail\"}";
            }
        }
        return msg;
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public String publicPage(){

        return "public";
    }

    @ResponseBody
    @RequestMapping(value = "/public", method = RequestMethod.POST)
    public String publicProduct(String imgType,@RequestParam(value = "file", required = false) MultipartFile file,Product product) throws IOException {
        String msg;
        if ("url".equals(imgType)){
            msg = productService.publicProduct(product);
        }else{
            String srcJson = edgeService.handleFileUpload(file);
            String status = JSON.parseObject(srcJson).getString("status");
            if ("success".equals(status)){
                String path = JSON.parseObject(srcJson).getString("path");
                product.setImageSrc(path);
                msg = productService.publicProduct(product);
            }else {
                msg = "{\"status\":\"fail\"}";
            }
        }
        return msg;
    }

    @RequestMapping(value = "/finance", method = RequestMethod.GET)
    public String orderPage(Model model){
        String orderMes = orderService.getOrderList(0,1000);
        List<Order> orderList = JSON.parseArray(orderMes,Order.class);
        List<OrderProductBundle> bundleList = new ArrayList<>();
        int total = 0;
        for (Order item : orderList){
            total += item.getCount() * item.getPriceDone();
            String productId = item.getProductId();
            String productJson = productService.getProduct(productId);
            Product product = JSON.parseObject(productJson,Product.class);
            OrderProductBundle bundle = new OrderProductBundle(item,product);
            bundleList.add(bundle);
        }
        model.addAttribute("bundleList",bundleList);
        model.addAttribute("total",total);
        return "order";
    }

    @RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
    public String cartPage(@RequestParam(defaultValue = "#") String from,Model model){
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
        model.addAttribute("from",from);
        model.addAttribute("bundleList",bundleList);
        return "cart";
    }






}
class OrderProductBundle{
    public Order order;
    public Product product;
    public OrderProductBundle(Order order,Product product){
        this.order = order;
        this.product = product;
    }
}
class CartProductBundle{
    public Cart cart;
    public Product product;
    public CartProductBundle(Cart cart,Product product){
        this.cart = cart;
        this.product = product;
    }
}