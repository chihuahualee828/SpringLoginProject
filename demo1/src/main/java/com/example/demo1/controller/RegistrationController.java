package com.example.demo1.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo1.entity.Account;
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
    
    

    
    @PostMapping("/register_page")
//    @RequestMapping(value ="/register_page", method = RequestMethod.POST)
    public String createAccount(@Valid @ModelAttribute("account") Account account, ModelMap modelMap) {
    	
    	try {
			demo1Service.getAccountByName(account.getName());
			modelMap.addAttribute("errorMessage", "This user already exists.");
			return "register";
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Username valid");
		}
    	
        Boolean emailBlank=false;
        if(account.getEmailAddress()=="" || account.getEmailAddress()==null) {
        	emailBlank=true;
        }
        
        if(emailBlank==false) {
        	try {
            	demo1Service.getAccountByEmail(account.getEmailAddress());
            	modelMap.addAttribute("errorMessage", "This email address is already used.");
    			return "register";
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("Email valid");
			}
        }
        
        
        
    	try {
//    		Role role=new Role();
//            Set<Role> roles = new HashSet<>();
////            role.setId(Long.valueOf(1));
//            role.setName("USER");
//            roles.add(role);
//            account.setRoles(roles);
    		demo1Service.createAccount(account);
    		modelMap.addAttribute("errorMessage", "account succesfully created!");
		} catch (Exception e) {
			System.out.println(e);
			modelMap.addAttribute("errorMessage", e);
		}
    	return "register";
        
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