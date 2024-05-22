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
public class PlanBoardDto {
	private String planBoardId;
	private String planId;
	private String memberId;
	private String nickname;
	private String image;
	private String subject;
	private String content;
	private String startDate;
	private String endDate;
	private	String theNumberOfMembers;
	private String registerTime;
	private String thumbnail;
	private String hit;
}
