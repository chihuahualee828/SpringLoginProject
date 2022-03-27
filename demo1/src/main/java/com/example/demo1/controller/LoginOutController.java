package com.example.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginOutController {
    @GetMapping("/login_page")
    public String login_page() {
        return "login";
    }

    @GetMapping("/logout_page")
    public String logout_page() {
        return "logout";
    }
    
    @GetMapping("/login_page?logout")
    public String logout_sucess() {
    	return "index";
    	
    }
    
    
}