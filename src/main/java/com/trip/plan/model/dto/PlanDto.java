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
public class PlanDto {
	private String planId;
	private String memberId;
	private String title;
	private String startDate;
	private String endDate;
}
