package com.doer.accountservice;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeTest {

    public static void main(String [] args){
        String passcode = new BCryptPasswordEncoder().encode("buyer");
        System.out.println(passcode);
    }
}
