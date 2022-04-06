package com.example.demo1.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo1.entity.Account;
import com.example.demo1.exception.NotFoundException;

public class SpringAccountService implements UserDetailsService {
    @Autowired
    private Demo1Service demo1Service;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        try {
            Account account = demo1Service.getAccount(Long.valueOf(userId));
//            List<SimpleGrantedAuthority> authorities = account.getAuthorities().stream()
//                    .map(auth -> new SimpleGrantedAuthority(auth.name()))
//                    .collect(Collectors.toList());
            
            return new User(account.getId().toString(), account.getPassword(), Collections.emptyList());
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException("User name is wrong.");
        }
    }
    
    
}