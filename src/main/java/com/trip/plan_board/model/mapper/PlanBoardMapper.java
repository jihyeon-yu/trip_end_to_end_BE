package com.trip.plan_board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;

@Mapper
public interface PlanBoardMapper {
	List<PlanBoardDto> listArticle();

	PlanBoardDto detailArticleById(String planBoardId);

	void updateHit(String planBoardId);

	List<PlanBoardTagDto> listTagById(String planBoardId);

	List<PlanCommentDto> listCommentById(String planBoardId);

	List<PlanLikeDto> listLikeById(String planBoardId);

	void insertArticle(PlanBoardDto planBoardDto);

	void deleteArticle(String planBoardId);

	void modifyArticle(PlanBoardDto planBoardDto);

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
