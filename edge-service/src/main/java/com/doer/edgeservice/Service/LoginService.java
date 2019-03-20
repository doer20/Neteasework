package com.doer.edgeservice.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    @Autowired
    private AccountService tokenService;

    public static String grant_type = "password";
    public static String scope = "webclient";
    public static String tokenKey = "access_token";

    public String authUser(){
        String userInfoJson = tokenService.getUserInfo();
        return userInfoJson;
    }

    public String getToken(String username,String password){
        String mes = tokenService.token(grant_type,scope,username,password);
        return JSON.parseObject(mes).getString(tokenKey);
    }
}
