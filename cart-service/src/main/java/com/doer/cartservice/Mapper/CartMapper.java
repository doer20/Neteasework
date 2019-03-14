package com.doer.cartservice.Mapper;

import com.doer.cartservice.Entities.Cart;

public interface CartMapper {
    int deleteByPrimaryKey(String cartId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(String cartId);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}