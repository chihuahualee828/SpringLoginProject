package com.example.demo1.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo1.entity.Account;
import com.example.demo1.exception.NotFoundException;
import com.example.demo1.service.Demo1Service;

import net.bytebuddy.utility.RandomString;

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
    
    @GetMapping("/forgot_pass")
    public String forgotPassword() {
        return "forgot";
    }
    
    @PostMapping("/forgot_pass")
    public String forgotPassword(HttpServletRequest request, ModelMap modelMap) {
    	String inputEmail = request.getParameter("email");
    	String inputUsername = request.getParameter("username");
        String token = RandomString.make(30);
        // UUID.randomUUID();
        Account account;
        try {
    		account = demo1Service.getAccountByName(inputUsername);
    	}catch (Exception e) {
    		modelMap.addAttribute("message", " User not found.");
    		return "forgot";
        }
    	
    	
    	String email  = account.getEmailAddress();
    	System.out.println(email);
    	System.out.println(inputEmail);
    	if (email == null || email.isEmpty()) {
    		modelMap.addAttribute("message", "User's email not found.");
    		return "forgot";
    	}
    	if (!email.equals(inputEmail)){
    		modelMap.addAttribute("message", "Email not match.");
    		return "forgot";
    	}
    	
    	request.getRequestURL().toString();
    	
    	try {
            demo1Service.updateResetPasswordToken(token, account);
            String siteURL = request.getRequestURL().toString();
            siteURL=siteURL.replace("forgot_pass", "");
            System.out.println(siteURL);
            String resetPasswordLink = siteURL + "reset_pass?token=" + token;
            demo1Service.sendResetPasswordLink(inputUsername, resetPasswordLink, email);
            modelMap.addAttribute("message", " Please check your email for password reset link.");
             
        } catch (NotFoundException ex) {
        	modelMap.addAttribute("message", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
        	modelMap.addAttribute("message", "Error while sending email");
        }
             
        return "forgot";
    }
    
    
    @GetMapping("/reset_pass")
    public String showResetPasswordForm(@Param(value = "token") String token, ModelMap modelMap) {
    	Account account = demo1Service.getByResetPasswordToken(token);
    	modelMap.addAttribute("token", token);
        
        if (account == null) {
        	modelMap.addAttribute("message", "Invalid Token");
        	return "login";
        }
         
        return "reset_password";
    }
    
    @PostMapping("/reset_pass")
    public String processResetPassword(HttpServletRequest request, ModelMap modelMap) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
         
        Account account = demo1Service.getByResetPasswordToken(token);
         
        if (account == null) {
        	modelMap.addAttribute("message", "Invalid Token");
        } else {           
            demo1Service.updatePassword(account, password);
            modelMap.addAttribute("message", "You have successfully changed your password.");
        }
         
        return "login";
    }

    
}