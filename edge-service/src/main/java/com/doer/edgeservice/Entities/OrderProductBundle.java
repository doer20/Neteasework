package com.doer.edgeservice.Entities;

public class OrderProductBundle{
    public Order order;
    public Product product;
    public OrderProductBundle(Order order,Product product){
        this.order = order;
        this.product = product;
    }
}