package com.example.demo1.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo1.config.MailConfig;
import com.example.demo1.entity.SystemMail;
import com.example.demo1.service.Demo1Service;
import com.example.demo1.service.MailService;

@Controller
public class SettingsController {
	
//	@Autowired
//    @Qualifier(MailConfig.GMAIL_SERVICE)
//    private MailService mailService;
    
	 
	@Autowired
	private MailConfig config;
	@Autowired
	private Demo1Service demo1Service;

    @PostMapping("/mail-config")
    //@ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> updateMailConfig(@RequestBody String request) {
    	
    	JSONObject jsonObject = new JSONObject(request);
    	String valueString=jsonObject.get("email").toString();
    	String valueString2=jsonObject.get("password").toString();
    	
//    	System.out.println("email:"+" "+valueString);
//    	System.out.println("app password:"+" "+valueString2);
    	
    	
    	config.set("username", valueString);
    	config.set("password", valueString2);
    	
    	demo1Service.updateSystemMailEmail(valueString);
    	demo1Service.updateSystemMailPass(valueString2);
    	
    	//return new ResponseEntity<>("This name is already used.", new HttpHeaders(), HttpStatus.CONFLICT);
    	return new ResponseEntity<>("saved",new HttpHeaders(), HttpStatus.OK);
    }
    
    
	@GetMapping("/system")
	public String getUser(Model model) {
		SystemMail systemMail = demo1Service.getSystemMail();
		model.addAttribute("email", systemMail);
		return "system_settings";
	}
	
	
}
