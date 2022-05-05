package com.example.demo1.config;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.example.demo1.service.SpringUserService;

//(debug = true)
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private SpringUserService userDetailsService; // inject springUserService to invoke loadUserByUsername
	
//	@Autowired
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http	
        		
                .authorizeRequests()
                //ADMIN 才可以看到所有使用者
                //.antMatchers(HttpMethod.GET, "/users").hasAuthority(UserAuthority.ADMIN.name())
//                .antMatchers(HttpMethod.GET, "/demo1/*").authenticated()
//                .antMatchers(HttpMethod.GET, "/demo1/**").authenticated()
//                .antMatchers(HttpMethod.GET).permitAll()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//              .antMatchers(HttpMethod.POST, "/users").permitAll()
//              .antMatchers("/").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .antMatchers("/view","/register_page").hasAnyAuthority("MANAGER", "ADMIN")
                .antMatchers("/h2-console/*").hasAnyAuthority("ADMIN")
                .antMatchers("/login_page","/perform_login","/forgot_pass","/reset_pass").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**") //so can log into h2-console 
                .and()
                .headers().frameOptions().disable() // so h2-console page will not be blank
                .and()
                
                //.csrf().disable()
                .formLogin()
	                .loginPage("/login_page")
	                .loginProcessingUrl("/perform_login")
//	                .failureUrl("/login_page?errorrrr")
//	                .usernameParameter("username")
//	                .passwordParameter("password")
//	                .permitAll()
                .and()
                .logout()
                	.logoutUrl("/perform_logout")
                	.logoutSuccessUrl("/login_page?logout")
                	.deleteCookies("JSESSIONID")
        		.and()
		        .sessionManagement()
				.maximumSessions(1)
				.sessionRegistry(sessionRegistry())
				.maxSessionsPreventsLogin(true);
			
                
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
    	SessionRegistry sessionRegistry = new SessionRegistryImpl();
    	return sessionRegistry;
    }
    
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) 
                .passwordEncoder(new BCryptPasswordEncoder());
        
    }
}