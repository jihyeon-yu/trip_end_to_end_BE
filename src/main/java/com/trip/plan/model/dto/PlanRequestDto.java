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
	private PlanDto planDto;
	private List<PlanGroupDto> memberIds;
	private List<BookGroupDto> bookContents;
	private List<PlanScheduleDto> scheduleDates;
	private List<PaymentDetailDto> paymentDetails;
	private List<List<PlanLocationDto>> planLocations;
}
