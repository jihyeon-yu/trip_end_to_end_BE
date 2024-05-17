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
public class AttractionInfoDto {
	private String contentId;
	private String contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String firstImage;
	private String firstImage2;
	private String readcount;
	private String sidoCode;
	private String gugunCode;
	private String latitude;
	private String longitude;
	private String mlevel;
	private String hit;
}
