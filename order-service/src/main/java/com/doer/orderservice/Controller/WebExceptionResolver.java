package com.doer.orderservice.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WebExceptionResolver implements HandlerExceptionResolver {
    private Logger log = LoggerFactory.getLogger(WebExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("Request {} Error！",httpServletRequest.getRequestURI());
        ModelAndView mv = new ModelAndView();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        String retStr = "{\"success\":false,\"msg\":\"Exception, please try later.\"}";
        try{
            httpServletResponse.getWriter().write(retStr);
        }catch(Exception ex){
            log.error("WebExceptionResolver Error",ex);
        }
        return mv;
    }
}