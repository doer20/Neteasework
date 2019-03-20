package com.doer.edgeservice.Service;

import com.doer.edgeservice.Config.FeignOauth2RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "payment-service", configuration = FeignOauth2RequestInterceptor.class)
public interface PaymentService {

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    String payment(@RequestParam("userId")String userId, @RequestParam("orderPrice")int orderPrice);
}
