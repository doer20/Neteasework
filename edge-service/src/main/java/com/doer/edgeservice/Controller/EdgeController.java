package com.doer.edgeservice.Controller;

import com.doer.edgeservice.Service.EdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EdgeController {

    @Autowired
    private EdgeService edgeService;

    @RequestMapping(value = "/buyAllInCart", method = RequestMethod.POST)
    public String buyAllInCart(){
        boolean flag = edgeService.buyAllInCart();
        return "{\"status\":"+flag+"}";
    }

}
