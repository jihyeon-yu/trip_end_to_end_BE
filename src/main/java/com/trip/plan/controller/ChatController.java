package com.trip.plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan.model.dto.ChatDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    
    @MessageMapping("/chat.sendMessage/{channel}")
    public void sendMessage(@DestinationVariable("channel") String channel, ChatDto message) {
    	if (!ChatDto.MessageType.SEND.equals(message.getType())) {
    		return ;
    	}
    	messagingTemplate.convertAndSend("/topic/" + channel, message);
    }
    
    @MessageMapping("/chat.addUser/{channel}")
    public void addUser(@DestinationVariable("channel") String channel, ChatDto message) {
        if (ChatDto.MessageType.JOIN.equals(message.getType())) {
            message.setContent(message.getNickname() + "님이 입장하셨습니다.");
        }
        messagingTemplate.convertAndSend("/topic/" + channel, message);
    }
    
    @MessageMapping("/chat.leaveUser/{channel}")
    public void leaveUser(@DestinationVariable("channel") String channel, ChatDto message) {
        if (ChatDto.MessageType.LEAVE.equals(message.getType())) {
            message.setContent(message.getNickname() + "님이 나가셨습니다.");
        }
        messagingTemplate.convertAndSend("/topic/" + channel, message);
    }
}