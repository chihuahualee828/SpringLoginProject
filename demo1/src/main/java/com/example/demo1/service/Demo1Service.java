package com.example.demo1.service;


import java.util.ArrayList;
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



import com.example.demo1.converter.AccountConverter;
import com.example.demo1.entity.Account;
import com.example.demo1.entity.AccountRequest;
import com.example.demo1.entity.AccountResponse;
import com.example.demo1.entity.Role;
import com.example.demo1.exception.NotFoundException;
import com.example.demo1.exception.UnprocessableEntityException;
import com.example.demo1.repository.AccountRepository;



@Service
public class Demo1Service {
	
	@Autowired
    private AccountRepository repository; // use DB
	private BCryptPasswordEncoder passwordEncoder;
    //delete @service and @autowired above to use this
//    public Demo1Service(AccountRepository repository) {
//        this.repository = repository;
//    }
    
//    @Autowired
//    private MockProductDAO productDAO; // use list as database 
    
	public Demo1Service(AccountRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
	
	
   
	public void createAccount(AccountRequest request) {
        Boolean userExist = repository.existsById(request.getId());
        Boolean emailValid=false;
        Role role=new Role();
        Set<Role> roles = new HashSet<>();
        if(request.getEmailAddress()=="" || request.getEmailAddress()==null) {
        	emailValid=true;
        }
        
        
        //put in controller
        //Optional<Account> existingEmail=Optional.of(existEmail);
        if (userExist) {
            throw new com.example.demo1.exception.UnprocessableEntityException("The user already exists .");
        }
        if(emailValid==false) {
        	Account existEmail=repository.findByEmailAddress(request.getEmailAddress());
        	if(existEmail!=null) {
        		throw new com.example.demo1.exception.UnprocessableEntityException("This email is already used .");
        	}
        }
        
        
        Account user = AccountConverter.toAccount(request);
        role.setId(Long.valueOf(1));
        role.setName("USER");
        roles.add(role);
        user.setRoles(roles);
        
        System.out.println(user.getId());
        System.out.println(user.getName());
        System.out.println(user.getPassword());
        System.out.println(user.getEmailAddress());
        System.out.println(user.getMobile());
        System.out.println(user.getIsActive());
        Role[] arr = user.getRoles().toArray(new Role[0]);
    	System.out.println("role: "+arr[0].getName());
        //encode password and then insert into repository
        //user.setPassword(request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        
        user = repository.save(user);
        
//        return AccountConverter.toAccountResponse(user);
    }

    public Account getAccount(Long id) {
//        return productDAO.find(id)
//                .orElseThrow(() -> new NotFoundException("Can't find product."));
    	return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find user id."));
    }
    
    public void updateAccount(AccountRequest request) {
    	
    	Account account=getAccount(request.getId());
//    	account.setIsActive(request.getIsActive());
//    	System.out.println(account.getId());
//    	System.out.println(account.getIsActive());
    	repository.save(account);
//    	return AccountConverter.toAccountResponse(account);
    }
    
    

    public AccountResponse getAccountResponseById(Long id) {
        Account user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Can't find user."));
        return AccountConverter.toAccountResponse(user);
    }

//    public Account getAccountByEmail(String email) {
//        return repository.findByEmailAddress(email)
//                .orElseThrow(() -> new NotFoundException("Can't find user."));
//    }

    public List<AccountResponse> getUserResponses() {
        List<Account> users = repository.findAll();
        return AccountConverter.toAccountResponses(users);
    }


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

