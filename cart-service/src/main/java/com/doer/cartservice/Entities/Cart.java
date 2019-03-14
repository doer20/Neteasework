package com.doer.cartservice.Entities;

public class Cart {
    private String cartId;

    private String productId;

    private Integer count;

    private Integer priceInCart;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId == null ? null : cartId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPriceInCart() {
        return priceInCart;
    }

    public void setPriceInCart(Integer priceInCart) {
        this.priceInCart = priceInCart;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart_id=" + cartId +
                ", product_id='" + productId + '\'' +
                ", count='" + count + '\'' +
                ", priceDone='" + priceInCart + '\'' +
                '}';
    }
}