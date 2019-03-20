package com.doer.cartservice.Mapper;

import com.doer.cartservice.Entities.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(String productId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(String productId);

    List<Cart> selectListOffset(@Param("offset") Integer offset, @Param("rowCount") Integer rowCount);

    Integer getCount();

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}