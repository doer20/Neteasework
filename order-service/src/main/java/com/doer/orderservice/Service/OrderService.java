package com.doer.orderservice.Service;

import com.doer.orderservice.Alog.SnowFlake;
import com.doer.orderservice.Entities.Order;
import com.doer.orderservice.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper mapper;

    private SnowFlake snowFlake = new SnowFlake(1,1,1);

    public List<Order> getOrderList(int offset,int rowCount){
        return mapper.getOrderList(offset,rowCount);
    }

    public boolean addOrder(Order order){
        boolean flag = true;
        try {
            long uniqueId = snowFlake.nextId();
            order.setOrderId(String.valueOf(uniqueId));
            if (order.getDate() == null){
                Date date = new Date();
                order.setDate(date);
            }
            mapper.insertSelective(order);
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public List<Order> getOrderByProductId(String product_id){
        return mapper.getOrderByProductId(product_id);
    }

    public int getCount(){
        return mapper.getCount();
    }

    public Order getOrderByOrderId(String order_id){
        return mapper.selectByPrimaryKey(order_id);
    }

    public boolean deleteOrder(String order_id){
        boolean flag = true;
        try {
            mapper.deleteByPrimaryKey(order_id);
        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean updateOrder(Order order){
        boolean flag = true;
        if (getOrderByOrderId(order.getOrderId()) == null){
            flag = false;
        }else {
            try {
                mapper.updateByPrimaryKeySelective(order);
            }catch (Exception e){
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

}
