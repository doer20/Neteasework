package com.doer.orderservice.Service;

import com.doer.orderservice.Entities.Order;
import com.doer.orderservice.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderMapper mapper;

    public Order getOrder(String order_id){
        return mapper.selectByPrimaryKey(order_id);
    }

}
