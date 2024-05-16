package com.trip.plan.model.dto;

import java.util.List;

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
public class PlanRequestDto {
	private String planId;
	private String title;
	private String startDate;
	private String endDate;
	private String[] memberIds;
	private String[] bookContents;
	private String[] scheduleDates;
	private List<PaymentDetailDto> paymentDetails;
	private List<PlanLocationDto> planLocations;
}
