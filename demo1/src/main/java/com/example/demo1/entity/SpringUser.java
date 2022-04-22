package com.example.demo1.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SpringUser implements UserDetails {
    private Account account;

    public SpringUser(Account account) {
        this.account = account;
    }

    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	Set<Role> roles = account.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        
//        if(account.getIsActive()) {
//        	authorities.add(new SimpleGrantedAuthority("ACTIVE"));
//        }
         
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getName();
    }
    
    public String getUserId() {
        return account.getId().toString();
    }
    
    public String getEmailAddress() {
        return account.getEmailAddress();
    }
    
    public Boolean getIsActive() {
        return account.getIsActive();
    }
    public int getMobile() {
    	return account.getMobile();
    }
    
    public String getDisplayName() {
    	return account.getDisplayName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getIsActive();
    }
    
    
}