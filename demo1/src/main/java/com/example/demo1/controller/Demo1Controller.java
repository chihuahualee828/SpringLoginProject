package com.example.demo1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    	model.addAttribute("users", demo1Service.getAccountsSort("asc","id"));
    	//System.out.println(model.getAttribute("users"));
    	return "view";
    }
    
    

    @PostMapping("/trigger-checkbox/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateActive(@PathVariable("id") String id, @RequestBody Account request) {

    	Account account = demo1Service.getAccount(Long.valueOf(id));

    	System.out.println(account.getId());
    	account.setIsActive(request.getIsActive());
    	System.out.println(account.getIsActive());
    	try {
    		demo1Service.updateAccount(AccountConverter.toAccountRequest(account));
		} catch (Exception e) {
			System.out.println(e);
		}
    	
    }
    
    @PostMapping("/save/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveChange(@PathVariable("id") String id, @RequestBody String request) {
    	
    	Account account = demo1Service.getAccount(Long.valueOf(id));
    	System.out.println(id);
    	
    	System.out.println(request);
    	JSONObject jsonObject = new JSONObject(request);
    	String keyString= jsonObject.keys().next().toString();
    	String valueString=jsonObject.get(keyString).toString();
    	
    	switch (keyString) {
		case "id":
			account.setId(Long.valueOf(valueString));
			break;
		case "name":
			account.setName(valueString);
			break;
		case "emailAddress":
			account.setEmailAddress(valueString);
			break;
		case "mobile":
			account.setMobile(Integer.valueOf(valueString));
			break;
		default:
			break;
		}
    	System.out.println(keyString+" "+valueString);
    	try {
    		demo1Service.updateAccount(AccountConverter.toAccountRequest(account));
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
    @GetMapping("/sortBy/{sort}")
    @ResponseStatus(value = HttpStatus.OK)
    public String sortBy(@PathVariable("sort") String sort, Model model) {
    	System.out.println(sort);
    	model.addAttribute("users", demo1Service.getAccountsSort("asc",sort));
    	System.out.println(model.getAttribute("users"));
    	return "view";
    }
    
    
}