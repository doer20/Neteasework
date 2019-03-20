package com.doer.edgeservice.Service;

import com.doer.edgeservice.Config.FeignOauth2RequestInterceptor;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "account-service", configuration = FeignOauth2RequestInterceptor.class)
public interface AccountService {

    @RequestMapping(value = "/oauth/token",method = RequestMethod.POST)
    String token(@RequestParam("grant_type") String grant_type,
                 @RequestParam("scope") String scope,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);


    @RequestMapping(value = "/oauth/user", method = RequestMethod.GET)
    String getUserInfo();
}
