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
public class PaymentDetailDto {
	private String paymentDetailId;
	private String memberId;
	private String content;
	private String date;
	private String price;
	private String planId;
}
