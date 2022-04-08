package com.example.demo1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo1.config.MailProperty;
import com.example.demo1.entity.Account;
import com.example.demo1.entity.Role;
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
    	//model.addAttribute("roles", demo1Service.getRolesSort("asc","id"));
    	//System.out.println(model.getAttribute("users"));
    	return "view";
    }
    
    

    @PostMapping("/trigger-checkbox/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateActive(@PathVariable("id") String id, @RequestBody Account request) {

    	Account account = demo1Service.getAccountById(Long.valueOf(id));
    	
    	System.out.println(account.getId());
    	account.setIsActive(request.getIsActive());
    	System.out.println(account.getIsActive());
    	try {
    		demo1Service.updateAccount(account);
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
    
    
    @PostMapping("/change-role/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRole(@PathVariable("id") String id, @RequestBody String request) {

    	Account account = demo1Service.getAccountById(Long.valueOf(id));
    	
    	JSONObject jsonObject = new JSONObject(request);
    	String keyString= jsonObject.keys().next().toString();
    	String valueString=jsonObject.get(keyString).toString();
    	
    	if(!account.getRoles().isEmpty()) {
        	System.out.println(account.getRoles().iterator().next().getName());
    	}
    	System.out.println("User ID "+id + "add role: " + valueString);
    	
//    	Role newRole = new Role();
//    	switch (valueString) {
//		case "USER":
////			newRole.setId(Long.valueOf(1));
//			newRole.setName("USER");
//			break;
//		case "MANAGER":
////			newRole.setId(Long.valueOf(2));
//			newRole.setName("MANAGER");
//			break;
//		case "ADMIN":
////			newRole.setId(Long.valueOf(3));
//			newRole.setName("ADMIN");
//			break;
//		default:
//			break;
//		}
    	
//    	Set<Role> roleSet = new HashSet<Role>();
//    	roleSet.add(newRole);
//    	account.setRoles(roleSet);
    	
    	try {
//    		demo1Service.updateAccount(account);
    		demo1Service.accountRoleMap(Long.valueOf(id), valueString);
    		
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
    @PostMapping("/role/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateRole(@PathVariable("id") String id, @RequestBody String request) {

    	
    	JSONObject jsonObject = new JSONObject(request);
    	String keyString= jsonObject.keys().next().toString();
    	String valueString=jsonObject.get(keyString).toString();
    	if(valueString=="true") {
    		try {
        		demo1Service.accountRoleMap(Long.valueOf(id), keyString);
        		
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}else {
    		try {
        		demo1Service.accountRoleDelete(Long.valueOf(id), keyString);
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    	
    }
    
    
    
    @PostMapping("/save/{id}")
    //@ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> saveChange(@PathVariable("id") String id, @RequestBody String request) {
    	
    	Account account = demo1Service.getAccountById(Long.valueOf(id));
    	System.out.println(id);
    	
    	JSONObject jsonObject = new JSONObject(request);
    	String keyString= jsonObject.keys().next().toString();
    	String valueString=jsonObject.get(keyString).toString();
    	System.out.println(keyString+" "+valueString);
    	
    	switch (keyString) {
//		case "id":
//			try {
//				demo1Service.getAccountById(Long.valueOf(valueString));
//				return new ResponseEntity<>("ID is already used.", new HttpHeaders(), HttpStatus.CONFLICT);
//			} catch (Exception e) {
//				Account newAccount = new Account();
//				newAccount.setId(Long.valueOf(valueString));
//				newAccount.setName(account.getName());
//				newAccount.setEmailAddress(account.getEmailAddress());
//				newAccount.setPassword(account.getPassword());
//				newAccount.setDisplayNmae(account.getDisplayName());
//				newAccount.setMobile(account.getMobile());
//				newAccount.setRoles(account.getRoles());
//				demo1Service.deleteAccount(Long.valueOf(id));
//				demo1Service.createAccount(newAccount);
//				return new ResponseEntity<>("updated",new HttpHeaders(), HttpStatus.OK);
//			}
		case "name":
			
			try {
				demo1Service.getAccountByName(valueString);
				return new ResponseEntity<>("This name is already used.", new HttpHeaders(), HttpStatus.CONFLICT);
			} catch (Exception e) {
				account.setName(valueString);
			}
			break;
		case "emailAddress":
			try {
				demo1Service.getAccountByEmail(valueString);
				return new ResponseEntity<>("Email is already used.", new HttpHeaders(), HttpStatus.CONFLICT);

			} catch (Exception e) {
				account.setEmailAddress(valueString);
			}
			break;
		case "mobile":
			account.setMobile(Integer.valueOf(valueString));
			break;
		default:
			break;
		}
    	
    	try {
    		demo1Service.updateAccount(account);
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ResponseEntity<>("updated",new HttpHeaders(), HttpStatus.OK);

    }
    
    @GetMapping("/sortBy/{sort}")
    @ResponseStatus(value = HttpStatus.OK)
    public String sortBy(@PathVariable("sort") String sort, Model model) {

    	model.addAttribute("users", demo1Service.getAccountsSort("asc",sort));
    	//model.addAttribute("roles", demo1Service.getRolesSort("asc",sort));

    	return "view";
    }
    
    
    @GetMapping("/search-by/{by}/{value}")
    @ResponseStatus(value = HttpStatus.OK)
    public String searchBy(@PathVariable("by") String by, @PathVariable("value") String value, Model model) {
    	
    	
    	System.out.println(by+" "+value);
//    	if(value=="") {
//			return "view";
//    	}
    	
    	switch (by) {
		case "id":
			List<Account> accounts = new ArrayList<Account>();
			try {
				Account account = demo1Service.getAccountById(Long.valueOf(value));
				accounts.add(account);
			}catch (Exception e) {
				System.out.println(e);
			}
			model.addAttribute("users", accounts);
			break;
		case "name":
			model.addAttribute("users", demo1Service.getAccountsByName(value));
			break;
		default:
			break;
		}
    	System.out.println(model.getAttribute("users"));
    	return "view";
    }
    
    
    
    
    
}