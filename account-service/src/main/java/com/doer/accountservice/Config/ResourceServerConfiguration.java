package com.doer.accountservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests().antMatchers("/**").permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/api/login")
//                .defaultSuccessUrl("/img/night.jpg").failureForwardUrl("/img/doubanjiangAndText2.png")
//                .permitAll().successHandler(loginSuccessHandler)
//                .and().logout().logoutSuccessUrl("/").permitAll();

//        http.antMatcher("/**").authorizeRequests()
//                .antMatchers("/oauth/**").permitAll()
//                .antMatchers("/login**").hasRole("ADMIN").anyRequest().authenticated();
        //关闭默认的csrf认证
//        http.csrf().disable();
        http.antMatcher("/**").authorizeRequests().antMatchers("/**").permitAll();
    }


}
