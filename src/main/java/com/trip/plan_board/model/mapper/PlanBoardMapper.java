package com.trip.plan_board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.plan_board.model.dto.AttractionDescriptionDto;
import com.trip.plan_board.model.dto.AttractionInfoDto;
import com.trip.plan_board.model.dto.PlanBoardFileInfoDto;
import com.trip.plan_board.model.dto.GugunDto;
import com.trip.plan_board.model.dto.SidoDto;
import com.trip.plan_board.model.dto.TagTypeDto;
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

	int insertArticle(PlanBoardDto planBoardDto);

	void deleteArticle(String planBoardId);

	void modifyArticle(PlanBoardDto planBoardDto);

	/* file */
	void registerFile(PlanBoardFileInfoDto fileInfo);

	PlanBoardFileInfoDto fileInfo(String planBoardId);
	
	void updateFile(PlanBoardFileInfoDto fileInfo);

	/* comment */
	void insertComment(PlanCommentDto planCommentDto);

	void deleteComment(String commentId);

	void modifyComment(PlanCommentDto planCommentDto);

	/* tag */
	void insertTag(PlanBoardTagDto planBoardTagDto);

	void deleteTag(String planBoardId);

	List<TagTypeDto> searchTag(String tagName);

	/* like */
	void insertLike(PlanLikeDto planLikeDto);

	void deleteLike(String planLikeId);

	/* map */
	List<SidoDto> getSidoList();

	List<GugunDto> getGugunList(String sidoCode);

	List<AttractionInfoDto> getAttractionInfoList(Map<String, String> map);

	AttractionDescriptionDto getAttractionDescription(String contentId);
}
