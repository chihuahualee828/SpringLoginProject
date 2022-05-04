package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo1.entity.Message;

public class ChatController {
	
	@GetMapping("/chat")
    public String chat_page() {
		System.out.println("sdfsdfdssdffsd");
        return "chat";
    }

	
	@MessageMapping("/chat.register")
	@SendTo("/user/public")
	public Message receivePublicMessage(@Payload Message message, SimpMessageHeaderAccessor hearAccessor) {
		hearAccessor.getSessionAttributes().put("username", message.getSenderName());
		return message;
	}
	
	@MessageMapping("/chat.send")
	@SendTo("/user/public")
	public Message receivePrivateMessage(@Payload Message message) {
		return message;
	}
	
	
}
