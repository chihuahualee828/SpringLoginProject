package com.example.demo1.entity;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface AccountDetails extends Serializable {
	Collection<? extends GrantedAuthority> getAuthorities();
	Long getUserId();
	String getPassword();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
