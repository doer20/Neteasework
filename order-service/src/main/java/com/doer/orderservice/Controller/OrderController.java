package com.doer.orderservice.Controller;

import com.doer.orderservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/orders/{order_id}")
    public String getOrder(@PathVariable String order_id){
        return orderService.getOrder(order_id).toString();
    }
}
