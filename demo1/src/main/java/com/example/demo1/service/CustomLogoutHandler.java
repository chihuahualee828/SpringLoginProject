package com.example.demo1.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.demo1.entity.SpringUser;

@Service
public class CustomLogoutHandler implements LogoutHandler {
	
	

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, 
      Authentication authentication) {
    	String userName = authentication.getName();
        SpringUser.loggedIn.remove(userName);
        System.out.println("loggedin after logout:");
        System.out.println(SpringUser.loggedIn);
    }
}
