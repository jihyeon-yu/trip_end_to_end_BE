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
public class QnACommentDto {
	private String commentId;
	private String qnaBoardId;
	private String memberId;
	private String nickname;
	private String content;
	private String depth;
	private String commentGroup;
	private String deleted;
	private String registerTime;
}
