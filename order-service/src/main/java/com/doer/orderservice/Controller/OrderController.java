package com.doer.orderservice.Controller;

import com.doer.orderservice.Entities.Order;
import com.doer.orderservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Order> getOrderList(int offset, int rowCount){
        return orderService.getOrderList(offset,rowCount);
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String addOrder(@RequestBody Order order){
        boolean flag = orderService.addOrder(order);
        return "{\"status\":"+flag+"}";
    }

    @ResponseBody
    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public List<Order> getOrderByProductId(@PathVariable String productId){
        return orderService.getOrderByProductId(productId);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String getOrderCount(){
        int count = orderService.getCount();
        return String.valueOf(count);
    }

    @Profile({"default","dev","test"})
    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public String getOrderByOrderId(@PathVariable String orderId){
        return orderService.getOrderByOrderId(orderId).toString();
    }

    @Profile({"default","dev","test"})
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteOrder(String orderId){
        boolean flag = orderService.deleteOrder(orderId);
        return "{\"status\":"+flag+"}";
    }

    @Profile({"default","dev","test"})
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateOrder(@RequestBody Order order){
        boolean flag = orderService.updateOrder(order);
        return "{\"status\":"+flag+"}";
    }
}
