package com.example.demo1.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

import com.example.demo1.service.MailService;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
	public static final String GMAIL_SERVICE = "gmailService";
    @Value("${mail.host}")
    private String host;

    @Value("${mail.port:25}") // 使用「:」符號可以加上預設值
    private int port;

    @Value("${mail.enable_auth}")
    private boolean authEnabled;

    @Value("${mail.enabled_starttls}")
    private boolean starttlsEnabled;

    @Value("${mail.protocol}")
    private String protocol;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

//    public String getHost() {
//    	return this.host;
//    }
//    public int getPort() {
//    	return this.port;
//    }
//    public String getUsername() {
//    	return this.username;
//    }
//    public String getPassword() {
//    	return this.password;
//    }
//    public String getProtocol() {
//    	return this.protocol;
//    }
//    public boolean isAuthEnabled() {
//    	return this.authEnabled;
//    }
//    public boolean isStarttlsEnabled() {
//    	return this.starttlsEnabled;
//    }
    
    //@Bean
    @Bean(name = GMAIL_SERVICE)
    @RequestScope
    public MailService mailService() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", authEnabled);
        props.put("mail.smtp.starttls.enable", starttlsEnabled);
        props.put("mail.transport.protocol", protocol);
        
        System.out.println("Mail Service is created.");
        
        return new MailService(mailSender);
    }
    
    
}