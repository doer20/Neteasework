package com.doer.orderservice.Entities;

import java.util.Date;

public class Order {
    private String orderId;

    private String productId;

    private Date date;

    private Integer count;

    private Integer priceDone;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPriceDone() {
        return priceDone;
    }

    public void setPriceDone(Integer priceDone) {
        this.priceDone = priceDone;
    }

    @Override
    public String toString() {
        return "Product{" +
                "order_id=" + orderId +
                ", product_id='" + productId + '\'' +
                ", date='" + date + '\'' +
                ", count='" + count + '\'' +
                ", priceDone='" + priceDone + '\'' +
                '}';
    }
}