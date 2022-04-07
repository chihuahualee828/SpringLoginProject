package com.example.demo1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.entity.SendMailRequest;
import com.example.demo1.config.MailConfig;
import com.example.demo1.config.MailProperty;
import com.example.demo1.service.MailService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/mail", produces = MediaType.APPLICATION_JSON_VALUE)
public class MailController {

    @Autowired
    @Qualifier(MailConfig.GMAIL_SERVICE)
    private MailService mailService;
    
    

    @PostMapping
    public ResponseEntity<Void> sendMail(@Valid @RequestBody SendMailRequest request) {
        mailService.sendMail(request);
        return ResponseEntity.noContent().build();
    }
    
    
    @Autowired
	private MailProperty prop;
	@GetMapping(path = "/mail-config/{key}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getUser(@PathVariable String key) {
		System.out.println(key);
		String result = prop.get(key);
		if (result == null) {
			result = "The value could not be obtained.";
		}
		System.out.println(result);
		return result;
	}

}