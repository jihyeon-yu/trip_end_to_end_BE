package com.trip.plan_board.model.service;

import java.util.List;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.request.PlanBoardDetailRequestDto;

public interface PlanBoardService {
	List<PlanBoardDto> listArticle();
	PlanBoardDetailRequestDto detailArticleById(String planBoardId);
}
