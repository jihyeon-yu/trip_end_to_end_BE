package com.trip.qna_board.model.dto.response;

import java.util.List;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;

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
public class QnABoardListItemDto {
	private String qnaBoardId;
	private String memberId;
	private String hit;
	private String subject;
	private String content;
	private String registerTime;
	private String secret;
	private String password;
	private String isAnswered;
	
	private String nickname;
}
