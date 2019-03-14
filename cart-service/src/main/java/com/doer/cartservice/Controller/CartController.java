package com.doer.cartservice.Controller;

import com.doer.cartservice.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/{id}")
    public String getCart(@PathVariable String cart_id){
        return cartService.getCart(cart_id).toString();
    }
}
