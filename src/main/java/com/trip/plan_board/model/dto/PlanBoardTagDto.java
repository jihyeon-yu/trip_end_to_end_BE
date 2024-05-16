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
public class PlanBoardTagDto {
	private String planBoardTagId;
	private String planBoardId;
	private String tagTypeId;
}
