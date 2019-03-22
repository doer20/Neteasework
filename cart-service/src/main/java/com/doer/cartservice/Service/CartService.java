package com.doer.cartservice.Service;

import com.doer.cartservice.Entities.Cart;
import com.doer.cartservice.Mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public Cart getItem(String product_id){
        Cart cart = cartMapper.selectByPrimaryKey(product_id);
        return cart;
    }

    public List<Cart> getCartList(int offset,int rowCount) {
        return cartMapper.selectListOffset(offset,rowCount);
    }

    public int getCount(){
        return cartMapper.getCount();
    }

    public boolean update(Cart cart){
        boolean flag = true;
        if (getItem(cart.getProductId()) == null){
            flag = false;
        }else {
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return flag;
    }

    public boolean delete(String productId){
        boolean flag = true;
        if (getItem(productId) != null){
            cartMapper.deleteByPrimaryKey(productId);
        }else {
            flag = false;
        }
        return flag;
    }

    public boolean addToCart(Cart cart){
        boolean flag = true;
        Cart item;
        if ((item = getItem(cart.getProductId())) != null){
            int totalCount = cart.getCount()+ item.getCount();
            cart.setCount(totalCount);
            try {
                cartMapper.updateByPrimaryKeySelective(cart);
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }else {
            try {
                cartMapper.insertSelective(cart);
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }

    public boolean increase(String product_id,int amount){
        boolean flag = true;
        Cart item;
        if ((item = getItem(product_id)) == null){
            flag = false;
        }else {
            int totalCount = item.getCount() + amount;
            item.setCount(totalCount);
            try {
                cartMapper.updateByPrimaryKeySelective(item);
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }

    public boolean decrease(String product_id,int amount){
        boolean flag = true;
        Cart item;
        if ((item = getItem(product_id)) == null){
            flag = false;
        }else if (item.getCount() < amount){
            flag = false;
        }else if (item.getCount() == amount){
            removeItem(product_id);
        }
        else {
            int totalCount = item.getCount() - amount;
            item.setCount(totalCount);
            try {
                cartMapper.updateByPrimaryKeySelective(item);
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }
        }
        return flag;
    }

    public void removeItem(String product_id){
        cartMapper.deleteByPrimaryKey(product_id);
    }
}
