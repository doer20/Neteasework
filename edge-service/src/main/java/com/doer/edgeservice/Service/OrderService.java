package com.doer.edgeservice.Service;

import com.doer.edgeservice.Config.FeignOauth2RequestInterceptor;
import com.doer.edgeservice.Entities.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "order-service", configuration = FeignOauth2RequestInterceptor.class)
public interface OrderService {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getOrderList(@RequestParam("offset")int offset, @RequestParam("rowCount")int rowCount);

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    String addOrder(Order order);

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String getOrderByProductId(@PathVariable(value = "productId") String productId);

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public String getOrderCount();

    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public String getOrderByOrderId(@PathVariable(value = "orderId") String orderId);

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String deleteOrder(String orderId);

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateOrder(Order order);
}
