package com.doer.productservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                "/list**",
                "/actuator/**",
                "/detail/**").permitAll()
                .antMatchers(
                        "/unsoldList**").hasAnyRole("SELLER","BUYER")
                .antMatchers(
                        "/addClosing**").hasRole("BUYER")
                .antMatchers(
                        "/deleteUnsold**",
                        "/addProduct**",
                        "/edit**",
                        "/uploadImg**").hasRole("SELLER")
                .antMatchers(
                        "/count**",
                        "/delete**",
                        "/getClosingNum**").hasRole("ADMIN").anyRequest().authenticated();
    }
}
