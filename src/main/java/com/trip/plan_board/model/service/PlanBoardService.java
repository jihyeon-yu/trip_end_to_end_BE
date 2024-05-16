package com.trip.plan_board.model.service;

import java.util.List;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.request.PlanBoardFormDto;
import com.trip.plan_board.model.dto.response.PlanBoardDetailDto;

public interface PlanBoardService {
	List<PlanBoardDto> listArticle();
	PlanBoardDetailDto detailArticleById(String planBoardId);
	void insertArticle(PlanBoardFormDto planBoardFormDto);
	void deleteArticle(String planBoardId);
	void modifyArticle(PlanBoardFormDto planBoardFormDto);
}
