package com.trip.plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan.model.dto.ChatDto;

@Controller
public class ChatRestController {
	@MessageMapping("/chat.sendMessage/{channel}")
    @SendTo("/topic/{channel}")
	public ChatDto sendMessage(ChatDto chatDto) {
        return chatDto;
    }
	
	@MessageMapping("/greeting")
	@SendTo("/topic/greeting")
    public String greeting(ChatDto message) throws Exception {
        return "Hello, " + message.getNickname();
    }

}
