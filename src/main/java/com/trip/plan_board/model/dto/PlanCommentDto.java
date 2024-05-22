package com.trip.plan_board.model.dto;

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
public class PlanCommentDto {
	private String commentId;
	private String planBoardId;
	private String memberId;
	private String nickname;
	private String image;
	private String content;
	private String depth;
	private String commentGroup;
	private String deleted;
	private String registerTime;
}
