package com.trip.notice_board.model.dto;

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
public class NoticeBoardDto {
	private String noticeBoardId;
	private String memberId;
	private String nickname;
	private String hit;
	private String subject;
	private String content;
	private String registerTime;
	private String isFixed;
}
