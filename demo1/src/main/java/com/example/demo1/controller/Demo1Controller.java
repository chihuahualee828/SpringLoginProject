package com.example.demo1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo1.converter.AccountConverter;
import com.example.demo1.entity.Account;
import com.example.demo1.entity.AccountRequest;
import com.example.demo1.service.Demo1Service;

@Controller
//@RequestMapping(value = "/demo1", produces = MediaType.APPLICATION_JSON_VALUE)
public class Demo1Controller {
	
	@Autowired
	private Demo1Service demo1Service;
	
    @GetMapping("/hello")
    public String hello(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", name); // set request attribute with key "name"
        return "hello"; // forward to hello.html
    }
    
    @GetMapping("/view")
    public String getAllAccounts(Model model) {
//    	ModelAndView mav = new ModelAndView();
//		mav.addObject("accounts", demo1Service.getAccounts());
//    	List<Object> accountList=new ArrayList<Object>();
//    	for(Account each : demo1Service.getAccounts()) {
//    		List<Object> eachAccountList=new ArrayList<Object>();
//    		eachAccountList.add(each.getId());
//    		eachAccountList.add(each.getName());
//    		eachAccountList.add(each.getPassword());
//    		accountList.add(eachAccountList);
//    	}
//    	List<Account> accounts=new ArrayList<Account>();
//    	for(int i = 0; i<=3; i++) {
//    		accounts.add(new Account());
//    	}
//    	model.addAttribute("users", accounts);
//    	List<Account> accounts = new ArrayList<Account>();
//    	Account account1 = new Account(Long.valueOf(6054), "BBB", "1233");
//    	accounts.add(account1);
    	model.addAttribute("users", demo1Service.getAccountsIdAsc());
    	System.out.println(model.getAttribute("users"));
		return "view";
    }
    
    

    @PostMapping("/trigger-checkbox")
    //@RequestMapping(value = "/view", params = "update", method = RequestMethod.POST)
    public String saveChange(@ModelAttribute("users") Account account,
    		Model model) {
    	
//    	for(Account account : accounts) {
//    		System.out.println(account);
//        	System.out.println(account.getId());
//        	System.out.println(account.getIsActive());
//        	AccountRequest request=AccountConverter.toAccountRequest(account);
//        	try {
//        		demo1Service.updateAccount(request);
//    		} catch (Exception e) {
//    			System.out.println(e);
//    		}
//    	}
    	AccountRequest request=AccountConverter.toAccountRequest(account);
    	try {
    		demo1Service.updateAccount(request);
		} catch (Exception e) {
			System.out.println(e);
		}
    	
		return "view";
    }
    

    
    
    
    
}