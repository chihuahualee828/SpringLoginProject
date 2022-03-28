package com.example.demo1.controller;


import java.net.URI;
import java.util.Map;

import javax.activation.MailcapCommandMap;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo1.converter.AccountConverter;
import com.example.demo1.entity.Account;
import com.example.demo1.entity.AccountRequest;
import com.example.demo1.entity.AccountResponse;
import com.example.demo1.service.Demo1Service;


@Controller
public class RegistrationController {
    
	@Autowired
	private Demo1Service demo1Service;
	
    @GetMapping("/register_page")
    public String register_page(Model model) {
    	model.addAttribute("account", new Account());
        return "register";
    }
    
    @GetMapping("/register_page?register_failed")
    public String register_failed() {
    	return "index";
    	
    }
    
    
//    @PostMapping("/register_page")
//    public String register(
//            @RequestParam(name = "username", required = true) String name,
//            @RequestParam(value = "userId", required = true) Long id,
//            @RequestParam(value = "password", required = true) String password,
//            @RequestParam(value = "mobile", required = false) int mobile,
//            @RequestParam(value = "email", required = false) String email) {
//        // set request attribute with key "name"
//        return "login"; // forward to hello.html
//    }
    
    @PostMapping("/register_page")
//    @RequestMapping(value ="/register_page", method = RequestMethod.POST)
    public String createAccount(@Valid @ModelAttribute("account") Account account) {
    	System.out.println(account);
    	AccountRequest request=AccountConverter.toAccountRequest(account);
    	try {
    		demo1Service.createAccount(request);
		} catch (Exception e) {
			System.out.println(e);
			return "register";
		}
    	
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(account.getId())
//                .toUri();
        return "login";
    }
    
//    @PostMapping("/register_page")
//    //Post and Put need @valid , Get does not need
//    public ResponseEntity<AccountResponse> createAccount1(@Valid @RequestParam Map<String, Object> request) {
//    	System.out.println(request.toString());
//    	AccountRequest accountRequest=new AccountRequest();
//    	accountRequest.setId(Long.valueOf(request.get("id").toString()));
//    	accountRequest.setName(request.get("name").toString());
//    	AccountResponse accountResponse = demo1Service.createAccount(accountRequest);
//    	System.out.println(accountResponse);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(accountResponse.getId())
//                .toUri();
//        
//        return ResponseEntity.created(location).body(accountResponse);
//    }
    
    
}