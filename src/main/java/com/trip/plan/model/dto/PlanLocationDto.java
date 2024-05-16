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
public class PlanLocationDto {
	private String planLocationId;
	private String planScheduleId;
	private String latitude;
	private String longitude;
	private String contentId;
	private String time;
}
