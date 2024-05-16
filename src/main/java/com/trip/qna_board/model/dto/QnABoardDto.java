package com.trip.qna_board.model.dto;

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
public class QnABoardDto {
	private String qnaBoardId;
	private String memberId;
	private String hit;
	private String subject;
	private String content;
	private String registerTime;
	private String secret;
	private String password;
	private String isAnswered;
}
