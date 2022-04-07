package com.example.demo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo1.service.SpringUserService;

//(debug = true)
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private SpringUserService userDetailsService; // inject springUserService to invoke loadUserByUsername
	
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
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
                .antMatchers("/login_page","/perform_login").permitAll()
                .antMatchers("/**").authenticated()
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
                	.deleteCookies("JSESSIONID");
        			
                
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService) 
                .passwordEncoder(bCryptPasswordEncoder());
        
    }
}