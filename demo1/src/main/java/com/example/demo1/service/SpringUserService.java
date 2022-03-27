package com.example.demo1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo1.entity.Account;
import com.example.demo1.entity.SpringUser;
import com.example.demo1.exception.NotFoundException;
import com.example.demo1.repository.AccountRepository;


@Service("SpringUserService")
public class SpringUserService implements UserDetailsService {
    @Autowired
    private Demo1Service demo1Service;

    @Autowired
    private AccountRepository accountRepository;
    
    public SpringUserService(AccountRepository accountRepository) {
		// TODO Auto-generated constructor stub
    	this.accountRepository=accountRepository;
	}
    
    
    //use to match input username/password with DB username/password, if not invoked, spring security uses auto generated username/password
    @Override
    public SpringUser loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            Account account1 = demo1Service.getAccount(Long.valueOf(username));
//            List<SimpleGrantedAuthority> authorities = account.getAuthorities().stream()
//                    .map(auth -> new SimpleGrantedAuthority(auth.name()))
//                    .collect(Collectors.toList());
//            System.out.println(account1.getPassword());
//            return new User(account1.getName(), account1.getPassword(), Collections.emptyList());
//        } catch (NotFoundException e) {
//            throw new UsernameNotFoundException("ID is wrong.");
//        }
    	Optional<Account> accounts = accountRepository.findById(Long.valueOf(username));
    	if (accounts.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
    	Account account=accounts.get();
    	Object[] arr = account.getRoles().toArray();
    	System.out.println(arr[0]);
    	//return new User(account.getId().toString(),account.getPassword(),Collections.emptyList());
        return new SpringUser(accounts.get());
    	
    }
    
    
    
    
}