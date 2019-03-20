package com.doer.orderservice.Mapper;

import com.doer.orderservice.Entities.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    List<Order> getOrderList(@Param("offset") Integer offset, @Param("rowCount")Integer rowCount);

    List<Order> getOrderByProductId(String ProductId);

    Integer getCount();

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}