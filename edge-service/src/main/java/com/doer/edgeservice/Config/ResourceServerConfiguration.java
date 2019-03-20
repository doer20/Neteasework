package com.doer.edgeservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    /*
     ** shared api **
     * getProductList
     * getProductDetail
     *
     ** buyer api **
     * getUnsoldProductList
     * getOrderList
     * getCartList
     * addIntoCart
     * increaseCartCount
     * decreaseCartCount
     * buyAllInCart (aggregate)
     *
     ** seller api **
     * deleteUnsoldProduct
     * addProduct
     * editProduct
     */

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.formLogin().permitAll()
//                // 登出页
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
//                .and().authorizeRequests().antMatchers(
//                "/buyAllInCart**").hasRole("BUYER").anyRequest().authenticated();

//        http
//                .antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/product/list**","/product/detail/**","/account/**","/*test/*","/login**","/product/**").permitAll()
//                .antMatchers("/buyAllInCart**").hasRole("BUYER")
//                .antMatchers("/upload/**").hasRole("SELLER").anyRequest().authenticated();

        http.antMatcher("/**").authorizeRequests().antMatchers("/**").permitAll();

//                .and().logout().logoutSuccessUrl("/").permitAll();
//        http.authorizeRequests().antMatchers("/product/**").hasRole("SELLER").anyRequest().authenticated();
//        http.authorizeRequests().antMatchers("/cart/**").hasRole("BUYER").anyRequest().authenticated();

    }
}
