package com.doer.edgeservice.Config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AccountInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String authKey = FeignOauth2RequestInterceptor.AUTHORIZATION_HEADER;
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie:cookies){
                if (authKey.equals(cookie.getName())){
                    String tokenHeader = cookie.getValue().replaceFirst("-"," ");
                    request.getSession().setAttribute(authKey,tokenHeader);
                    break;
                }
            }
        }
        String path = request.getServletPath();
        if (path.startsWith("/login")
                || path.startsWith("/home")
                || path.startsWith("/error")
                || path.startsWith("/detail")
                || path.startsWith("/upload")
                || path.startsWith("/temp")
                || path.startsWith("/img")) {
            return true;
        }
        if (request.getSession().getAttribute("isLogin") != null
                && (boolean)request.getSession().getAttribute("isLogin")){
            List<String> auths = (List<String>) request.getSession().getAttribute("authorities");
            if (path.startsWith("/shoppingCart")
                    || path.startsWith("/finance")
                    || path.startsWith("/unsold")
                    || path.startsWith("/cartFragment")
                    || path.startsWith("/increase")
                    || path.startsWith("/decrease")
                    || path.startsWith("/buyAllInCart")
                    || path.startsWith("/addIntoCart")){
                if (!auths.contains("ROLE_BUYER")){
                    response.sendRedirect("/home");
                    return false;
                }else {
                    return true;
                }
            }
            if (path.startsWith("/edit")
                    || path.startsWith("/deleteUnsold")
                    || path.startsWith("/public")){
                if (!auths.contains("ROLE_SELLER")){
                    response.sendRedirect("/home");
                    return false;
                }else {
                    return true;
                }
            }
        }
        response.sendRedirect("/login");
        return false;
    }
}

