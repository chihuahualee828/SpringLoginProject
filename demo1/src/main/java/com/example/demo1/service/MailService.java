package com.example.demo1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.example.demo1.entity.SendMailRequest;
import com.example.demo1.config.MailConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Service
public class MailService {
	
	@Autowired
    private MailConfig mailConfig;
	
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

//    private static final String HOST = "smtp.gmail.com";
//    private static final int PORT = 587;
//    private static final boolean ENABLE_AUTH = true;
//    private static final boolean ENABLE_STARTTLS = true;
//    private static final String PROTOCOL = "smtp";
//    private static final String USERNAME = "nick29479478@gmail.com";
//    private static final String PASSWORD = "twaiftzrjkrtywbm";
//    private JavaMailSenderImpl mailSender;
    private final JavaMailSenderImpl mailSender;
    private final String LOG_EMAIL;
    private final long tag;
    private final List<String> mailMessages;
    
//    @PostConstruct
//    private void init() {
//        mailSender = new JavaMailSenderImpl();
////        mailSender.setHost(HOST);
////        mailSender.setPort(PORT);
////        mailSender.setUsername(USERNAME);
////        mailSender.setPassword(PASSWORD);
////
////        Properties props = mailSender.getJavaMailProperties();
////        props.put("mail.smtp.auth", ENABLE_AUTH);
////        props.put("mail.smtp.starttls.enable", ENABLE_STARTTLS);
////        props.put("mail.transport.protocol", PROTOCOL);
//        
//        //external config
//        mailSender.setHost(mailConfig.getHost());
//        mailSender.setPort(mailConfig.getPort());
//        mailSender.setUsername(mailConfig.getUsername());
//        mailSender.setPassword(mailConfig.getPassword());
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth", mailConfig.isAuthEnabled());
//        props.put("mail.smtp.starttls.enable", mailConfig.isStarttlsEnabled());
//        props.put("mail.transport.protocol", mailConfig.getProtocol());
//    }
    //alternative to above
    public MailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
        this.LOG_EMAIL = mailSender.getUsername();
        this.tag = System.currentTimeMillis();
        this.mailMessages = new ArrayList<>();
    }
    
    public void sendMail(SendMailRequest request) {
        sendMail(request.getSubject(), request.getContent(), request.getReceivers());
    }

    public void sendMail(String subject, String content, List<String> receivers) {
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(USERNAME);
        message.setFrom(mailSender.getUsername());
        message.setTo(receivers.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            mailMessages.add(content);
            printMessages();
        } catch (MailAuthenticationException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }
    
    //Used in demo1 service
    public void sendCreateNewUserMail(String id, String name, String password, String receiverEmail) {
    	String mailContent = "Your id: " + id + "\n" +  "Your username: " + name + "\n" 
    			+ "Your pssword: " +password;
    	
        System.out.println("receiver:"+receiverEmail);
        sendMail("User Created", mailContent,
                Collections.singletonList(receiverEmail));
    }
    public void sendDeleteProductMail(String productId) {
        String content = String.format("There's a product deleted (%s).", productId);
        sendMail("Product Deleted", content,
                Collections.singletonList(LOG_EMAIL));
    }
    
    
    private void printMessages() {
        System.out.println("----------");
        mailMessages.forEach(System.out::println);
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("##########");
        System.out.printf("Spring Boot is about to destroy Mail Service %d.\n\n", tag);
    }
}