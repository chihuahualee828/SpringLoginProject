package com.example.demo1.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.standard.expression.Each;

import com.example.demo.service.MailService;
import com.example.demo1.entity.Account;
import com.example.demo1.entity.Role;
import com.example.demo1.exception.NotFoundException;
import com.example.demo1.exception.UnprocessableEntityException;
import com.example.demo1.repository.AccountRepository;



@Service
public class Demo1Service {
	
	@Autowired
    private AccountRepository repository; // use DB
	private BCryptPasswordEncoder passwordEncoder;
	private MailService mailService;
    //delete @service and @autowired above to use this
//    public Demo1Service(AccountRepository repository) {
//        this.repository = repository;
//    }
    
	public Demo1Service(AccountRepository repository, MailService mailService) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.mailService = mailService;
    }
	
	
   
	public void createAccount(Account request) {
		
		
        Role role=new Role();
        Set<Role> roles = new HashSet<>();
//        Account user = AccountConverter.toAccount(request);
        role.setId(Long.valueOf(1));
        role.setName("USER");
        roles.add(role);
        request.setRoles(roles);
        
        System.out.println(request.getId());
        System.out.println(request.getName());
        System.out.println(request.getPassword());
        System.out.println(request.getEmailAddress());
        System.out.println(request.getMobile());
        System.out.println(request.getIsActive());
        Role[] arr = request.getRoles().toArray(new Role[0]);
    	System.out.println("role: "+arr[0].getName());
        //encode password and then insert into repository
        //user.setPassword(request.getPassword());
    	String decodedPassword = request.getPassword();
    	request.setPassword(passwordEncoder.encode(request.getPassword()));
    	request = repository.save(request);
    	
        mailService.sendCreateNewUserMail(request.getId().toString(), request.getName(), decodedPassword, request.getEmailAddress());

        
//        return AccountConverter.toAccountResponse(user);
    }
	
	public Account deleteAccount(Long id){
		Account account = getAccountById(id);
		repository.deleteById(id);
		return account;
		 
	}

    public Account getAccountById(Long id) {
    	return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find user id."));
    }
    
    public Account getAccountByEmail(String email) {
    	Optional<Account> accountOptional = Optional.of(repository.findByEmailAddress(email));
    	return accountOptional
                .orElseThrow(() -> new NotFoundException("Can't find email."));
    }
    
    public List<Account> getAccountsByName(String name) {
    	Optional<List<Account>> accountOptional = Optional.of(repository.findAccountsByName(name));
    	return accountOptional
                .orElseThrow(() -> new NotFoundException("Can't find name."));
    }
    
    
    public void updateAccount(Account request) {
    	
    	Account account=getAccountById(request.getId());
//    	account.setIsActive(request.getIsActive());
//    	System.out.println(account.getId());
//    	System.out.println(account.getIsActive());
    	repository.save(account);
//    	return AccountConverter.toAccountResponse(account);
    }
    
    
    
//    public AccountResponse getAccountResponseById(Long id) {
//        Account user = repository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Can't find user."));
//        return AccountConverter.toAccountResponse(user);
//    }

//    public Account getAccountByEmail(String email) {
//        return repository.findByEmailAddress(email)
//                .orElseThrow(() -> new NotFoundException("Can't find user."));
//    }

//    public List<AccountResponse> getUserResponses() {
//        List<Account> users = repository.findAll();
//        return AccountConverter.toAccountResponses(users);
//    }


    public List<Account> getAccounts() {
//        return productDAO.find(param);
//    	String keyword = Optional.ofNullable(param.getKeyword()).orElse("");
////        int priceFrom = Optional.ofNullable(param.getPriceFrom()).orElse(0);
////        int priceTo = Optional.ofNullable(param.getPriceTo()).orElse(Integer.MAX_VALUE);
//        
//        Sort sort = genSortingStrategy(param.getOrderBy(), param.getSortRule());
//    	List<Account> accountList= new ArrayList<Account>();
//    	for(Account each : repository.findAll()) {
//    		accountList.add(each);
//    	}
    	
        return repository.findAll();
    	
    }
    
    public List<Account> getAccountsSort(String asc, String sortBy) {
    	if(asc=="asc") {
    	     return repository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
    	}else {
    	     return repository.findAll(Sort.by(Sort.Direction.DESC, sortBy));
    	}
  	
    }
    
    public List<Set<Role>> getRolesSort(String asc, String sortBy) {
    	List<Set<Role>> roles = new ArrayList<Set<Role>>();
    	if(asc=="asc") {
    	     for(Account each:repository.findAll(Sort.by(Sort.Direction.ASC, sortBy))) {
    	    	 roles.add(each.getRoles());
//    	    	 Set<Integer> roleIds = new HashSet<Integer>();
//    	    	 while(each.getRoles().iterator().hasNext()) {
//    	    		 roleIds.add(each.getRoles().iterator().next().getId().intValue());
//    	    	 }
//    	    	 Collections.max(roleIds);
    	    	 
    	     }
    	}else {
    		for(Account each:repository.findAll(Sort.by(Sort.Direction.DESC, sortBy))) {
    			roles.add(each.getRoles());
   	     }
    	}
    	
    	return roles;
  	
    }
    
   
   
//    private Sort genSortingStrategy(String orderBy, String sortRule) {
//        Sort sort = Sort.unsorted();
//        if (Objects.nonNull(orderBy) && Objects.nonNull(sortRule)) {
//            Sort.Direction direction = Sort.Direction.fromString(sortRule);
//            sort = Sort.by(direction, orderBy);
//        }
//
//        return sort;
//    }
    
    
    
}

