package com.doer.edgeservice.Service;

import com.doer.edgeservice.Config.FeignOauth2RequestInterceptor;
import com.doer.edgeservice.Entities.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cart-service", configuration = FeignOauth2RequestInterceptor.class)
public interface CartService {

    @RequestMapping(value = "/detail/{productId}",method = RequestMethod.GET)
    String getItem(@PathVariable(value = "productId") String productId);


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    String addToCart(Cart cart);

    @RequestMapping(value = "/increase", method = RequestMethod.POST)
    String increase(@RequestParam("productId")String productId, @RequestParam("amount") int amount);

    @RequestMapping(value = "/decrease", method = RequestMethod.POST)
    String decrease(@RequestParam("productId")String productId,@RequestParam("amount")int amount);

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    String getCartList(@RequestParam("offset")int offset,@RequestParam("rowCount")int rowCount);

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    String getCount();

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    String update(Cart cart);

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.POST)
    String delete(@PathVariable(value = "productId") String productId);
}
