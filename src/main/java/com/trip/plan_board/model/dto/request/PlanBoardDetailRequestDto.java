package com.trip.plan_board.model.dto.request;

import java.util.List;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;

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
public class PlanBoardDetailRequestDto {
	private PlanBoardDto planBoard;
	private List<PlanCommentDto> commentList;
	private List<PlanBoardTagDto> tagList;
	private List<PlanLikeDto> likeList;
}
