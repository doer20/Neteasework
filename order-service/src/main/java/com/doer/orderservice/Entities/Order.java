package com.doer.orderservice.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
        return "{\"orderId\":\""+orderId+"\"," +
                "\"productId\":\""+productId+"\"," +
                "\"date\":\""+date+"\"," +
                "\"count\":"+count+"," +
                "\"priceDone\":"+priceDone+"}";
    }
}