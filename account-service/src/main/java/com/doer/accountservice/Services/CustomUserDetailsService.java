package com.doer.accountservice.Services;

import com.doer.accountservice.Entities.User;
import com.doer.accountservice.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByPrimaryKey(s);

        if (user == null){
            throw new UsernameNotFoundException("UserName:" + s + " not found");
        }
        List<GrantedAuthority> authList = new ArrayList<>();
        if (user.getRole().contains("buyer")){
            authList.add(new SimpleGrantedAuthority("ROLE_BUYER"));
        }
        if (user.getRole().contains("seller")){
            authList.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        }
        if (user.getRole().contains("admin")){
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User
                (s,user.getUserPass(),true,true,true,true,authList);
        return userDetails;
    }
}
