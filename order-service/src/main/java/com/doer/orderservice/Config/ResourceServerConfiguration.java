package com.doer.orderservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/actuator/**").permitAll()
                .antMatchers(
                "/list**",
                "/addOrder**",
                "/product**",
                "/count**").hasRole("BUYER")
                .antMatchers(
                "/detail/**",
                "/delete**",
                "/edit**").hasRole("ADMIN").anyRequest().authenticated();
    }
}
