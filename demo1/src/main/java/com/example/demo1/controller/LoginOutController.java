package com.example.demo1.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginOutController {
	private boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}
	
    @GetMapping("/login_page")
    public String login_page() {
    	if (isAuthenticated()) {
            return "redirect:";
        }
        return "login";
    }

//    @GetMapping("/logout_page")
//    public String logout_page() {
//        return "logout";
//    }
    
    @GetMapping("/login_page?logout")
    public String logout_sucess() {
    	return "index";
    }
    
    
}