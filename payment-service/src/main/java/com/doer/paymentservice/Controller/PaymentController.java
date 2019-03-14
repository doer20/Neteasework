package com.doer.paymentservice.Controller;

import com.doer.paymentservice.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payment(String user_id,int orderPrice){
        boolean flag = paymentService.payment(user_id,orderPrice);
        return "{'status':"+ flag + "}";
    }
}
