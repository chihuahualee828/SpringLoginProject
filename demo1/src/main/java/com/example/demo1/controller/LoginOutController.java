package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo1.entity.Account;
import com.example.demo1.service.Demo1Service;

@Controller
public class LoginOutController {
	
	@Autowired
	private Demo1Service demo1Service;
	
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
    
    @GetMapping("/reset_pass")
    public String resetPassword() {
        return "reset";
    }
    
    @PostMapping("/reset_pass/{username}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateActive(@PathVariable("username") String username) {

    	Account account = demo1Service.getAccountByName(username);
    	
    	String email  = account.getEmailAddress();
    	try {
    		demo1Service.updateAccount(account);
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
}