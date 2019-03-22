package com.doer.edgeservice.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doer.edgeservice.Entities.*;
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
        List<String> auths = (List<String>) httpSession.getAttribute("authorities");
        if (auths != null && auths.contains("ROLE_BUYER")){
            int cartCount = Integer.parseInt(cartService.getCount());
            model.addAttribute("cartCount",cartCount);
        }
        return "home";
    }

    @RequestMapping(value = "/unsold", method = RequestMethod.GET)
    public String unsoldPage(Model model,HttpSession httpSession){
        String unsoldMes = productService.getUnsoldList();
        List<Product> unsoldList = JSON.parseArray(unsoldMes,Product.class);
        model.addAttribute("unsoldList",unsoldList);
        List<String> auths = (List<String>) httpSession.getAttribute("authorities");
        if (auths != null && auths.contains("ROLE_BUYER")){
            int cartCount = Integer.parseInt(cartService.getCount());
            model.addAttribute("cartCount",cartCount);
        }
        return "unsold";
    }


    @ResponseBody
    @RequestMapping(value = "/deleteUnsold",method = RequestMethod.POST)
    public String deleteUnsold(String productId){
        String productMsg = productService.deleteUnsoldProduct(productId);
        boolean deleteProductFlag = JSON.parseObject(productMsg).getBoolean("status");
        JSONObject res = new JSONObject();
        res.put("deleteProductFlag",deleteProductFlag);
        String cartMsg = cartService.delete(productId);
        boolean deleteCartFlag = JSON.parseObject(cartMsg).getBoolean("status");
        res.put("deleteCartFlag",deleteCartFlag);
        return res.toJSONString();
    }

    @RequestMapping(value = "/detail/{productId}",method = RequestMethod.GET)
    public String detailPage(Model model,@PathVariable String productId,HttpSession httpSession){
        String productJson = productService.getProduct(productId);
        Product product = JSON.parseObject(productJson,Product.class);
        if (product.getProductId() == null){
            return "redirect:/home";
        }
        List<String> auths = (List<String>) httpSession.getAttribute("authorities");
        if (auths != null && auths.contains("ROLE_BUYER")){
            int cartCount = Integer.parseInt(cartService.getCount());
            model.addAttribute("cartCount",cartCount);
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
        return "product";
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
        int cartCount = Integer.parseInt(cartService.getCount());
        model.addAttribute("cartCount",cartCount);
        return "edit";
    }

    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(String imgType,@RequestParam(value = "file", required = false) MultipartFile file,Product product) throws IOException {
        String msg;
        Cart cart = JSON.parseObject(cartService.getItem(product.getProductId()),Cart.class);
        if (cart.getProductId() != null){
            cart.setPriceInCart(product.getPrice());
            cartService.update(cart);
        }
        Product originProduct = JSON.parseObject(productService.getProduct(product.getProductId()),Product.class);
        if ("url".equals(imgType)){
            msg = productService.updateProduct(product);
        }else{
            String srcJson = edgeService.handleFileUpload(file);
            String status = JSON.parseObject(srcJson).getString("status");
            if ("success".equals(status)){
                String path = JSON.parseObject(srcJson).getString("path");
                edgeService.deleteFile(originProduct.getImageSrc());
                product.setImageSrc(path);
                msg = productService.updateProduct(product);
            }else {
                msg = "{\"status\":\"fail\"}";
            }
        }
        return msg;
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public String publicPage(Model model){
        int cartCount = Integer.parseInt(cartService.getCount());
        model.addAttribute("cartCount",cartCount);
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
        List<OrderProductBundle> orderProductBundleList = new ArrayList<>();
        int total = 0;
        for (Order item : orderList){
            total += item.getCount() * item.getPriceDone();
            String productId = item.getProductId();
            String productJson = productService.getProduct(productId);
            Product product = JSON.parseObject(productJson,Product.class);
            OrderProductBundle bundle = new OrderProductBundle(item,product);
            orderProductBundleList.add(bundle);
        }
        model.addAttribute("orderBundleList",orderProductBundleList);
        model.addAttribute("total",total);
        int cartCount = Integer.parseInt(cartService.getCount());
        model.addAttribute("cartCount",cartCount);
        return "finance";
    }

    @RequestMapping(value = "/shoppingCart", method = RequestMethod.GET)
    public String cartPage(Model model){
        List<CartProductBundle> bundleList = edgeService.getCart();
        int cartSummary = edgeService.getCartSummary();
        int cartCount = Integer.parseInt(cartService.getCount());
        model.addAttribute("bundleList",bundleList);
        model.addAttribute("cartCount",cartCount);
        model.addAttribute("cartSummary",cartSummary);
        return "cart";
    }

    @RequestMapping(value = "/cartFragment", method = RequestMethod.GET)
    public String cartFragment(Model model){
        List<CartProductBundle> bundleList = edgeService.getCart();
        int cartSummary = edgeService.getCartSummary();
        int cartCount = Integer.parseInt(cartService.getCount());
        model.addAttribute("bundleList",bundleList);
        model.addAttribute("cartCount",cartCount);
        model.addAttribute("cartSummary",cartSummary);
        return "cartFragment";
    }





}