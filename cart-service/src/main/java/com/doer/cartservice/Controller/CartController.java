package com.doer.cartservice.Controller;

import com.doer.cartservice.Entities.Cart;
import com.doer.cartservice.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/detail/{productId}",method = RequestMethod.GET)
    public String getItem(@PathVariable String productId){
        return cartService.getItem(productId).toString();
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String getCartList(int offset,int rowCount){
        return cartService.getCartList(offset,rowCount).toString();
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String addToCart(@RequestBody Cart cart){
        boolean flag = cartService.addToCart(cart);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/increase", method = RequestMethod.POST)
    public String increase(String productId,int amount){
        boolean flag = cartService.increase(productId,amount);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/decrease", method = RequestMethod.POST)
    public String decrease(String productId,int amount){
        boolean flag = cartService.decrease(productId, amount);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String getCount(){
        int count = cartService.getCount();
        return String.valueOf(count);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody Cart cart){
        boolean flag = cartService.update(cart);
        return "{\"status\":"+flag+"}";
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.POST)
    public String delete(@PathVariable(value = "productId") String productId){
        boolean flag = cartService.delete(productId);
        return "{\"status\":"+flag+"}";
    }

}
