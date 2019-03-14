package com.doer.accountservice.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloService {

    @RequestMapping("/hello")
    public String hello(){
        return "hello world_account-service";
    }

}
