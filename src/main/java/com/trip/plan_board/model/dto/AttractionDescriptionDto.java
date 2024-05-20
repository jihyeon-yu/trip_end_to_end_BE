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
public class AttractionDescriptionDto {
	private String contentId;
	private String homepage;
	private String overview;
	private String telName;

}
