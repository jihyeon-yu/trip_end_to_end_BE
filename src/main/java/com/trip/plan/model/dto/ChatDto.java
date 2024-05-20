package com.trip.plan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatDto {
	public enum MessageType {
		JOIN, SEND, LEAVE
	}
	private String nickname;
	private String content;
	private MessageType type;
}
