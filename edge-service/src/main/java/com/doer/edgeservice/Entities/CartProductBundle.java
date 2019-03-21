package com.doer.edgeservice.Entities;

public class CartProductBundle{
    public Cart cart;
    public Product product;
    public CartProductBundle(Cart cart,Product product){
        this.cart = cart;
        this.product = product;
    }
}