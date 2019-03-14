package com.doer.cartservice.Service;

import com.doer.cartservice.Entities.Cart;
import com.doer.cartservice.Mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public Cart getCart(String cart_id){
        return cartMapper.selectByPrimaryKey(cart_id);
    }
}
