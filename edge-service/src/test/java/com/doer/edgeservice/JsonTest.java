package com.doer.edgeservice;

import com.alibaba.fastjson.JSON;
import com.doer.edgeservice.Entities.Cart;

import java.util.List;

public class JsonTest {


    public static void main(String [] args){
        String json = "[{\"productId\":\"1\",\"count\":10,\"priceInCart\":100},{\"productId\":\"2\",\"count\":10,\"priceInCart\":100}]";
        List<Cart> carts = JSON.parseArray(json,Cart.class);
        System.out.println(carts);
    }
}
