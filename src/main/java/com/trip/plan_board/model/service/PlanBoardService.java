package com.trip.plan_board.model.service;

import java.util.List;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;
import com.trip.plan_board.model.dto.request.PlanBoardFormDto;
import com.trip.plan_board.model.dto.response.PlanBoardDetailDto;

public interface PlanBoardService {
	List<PlanBoardDto> listArticle();

	PlanBoardDetailDto detailArticleById(String planBoardId);

	void insertArticle(PlanBoardFormDto planBoardFormDto);

	void deleteArticle(String planBoardId);

	void modifyArticle(PlanBoardFormDto planBoardFormDto);

	void updateHit(String planBoardId);

	/* comment */
	void insertComment(PlanCommentDto planCommentDto);

	void deleteComment(String commentId);

	void modifyComment(PlanCommentDto planCommentDto);

	/* tag */
	void insertTag(PlanBoardTagDto planBoardTagDto);

	void deleteTag(String planBoardTagId);

	/* like */
	void insertLike(PlanLikeDto planLikeDto);

	void deleteLike(String planLikeId);

}
