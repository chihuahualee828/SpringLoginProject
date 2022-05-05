package com.example.demo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

public class ConcurrentSessionControlStrategy extends ConcurrentSessionControlAuthenticationStrategy  {
	
	protected int getMaximumSessionsForThisUser(Authentication authentication) {
        return 1;

    }
}