package com.example.demo1.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.demo1.entity.SpringUser;

@Service
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException, ServletException {
        	
        	String userName = authentication.getName();
        	
        	SpringUser.loggedIn.add(userName);
        	System.out.println("loggedin:");
        	System.out.println(SpringUser.loggedIn);
        	
        	response.sendRedirect(request.getContextPath());
        }
    
}
