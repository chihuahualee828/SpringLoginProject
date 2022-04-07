package com.example.demo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:mail.properties")
public class MailProperty {
	//Inject Environment
	@Autowired
	private ConfigurableEnvironment env;
	
	public String get(String key) {
		//Get property value from Environment\
		return env.getProperty("mail." + key);
	}
}
