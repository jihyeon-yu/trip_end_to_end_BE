package com.trip.plan_board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.trip.plan_board.model.dto.AttractionDescriptionDto;
import com.trip.plan_board.model.dto.AttractionInfoDto;
import com.trip.plan_board.model.dto.GugunDto;
import com.trip.plan_board.model.dto.SidoDto;
import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;
import com.trip.plan_board.model.dto.request.PlanBoardFormDto;
import com.trip.plan_board.model.dto.response.PlanBoardDetailDto;
import com.trip.plan_board.model.mapper.PlanBoardMapper;

@Service
public class PlanBoardServiceImpl implements PlanBoardService {
	private PlanBoardMapper planBoardMapper;
	
	public PlanBoardServiceImpl(PlanBoardMapper planBoardMapper) {
		super();
		this.planBoardMapper = planBoardMapper;
	}
	
	@Override
	public List<PlanBoardDto> listArticle() {
		return planBoardMapper.listArticle();
	}

	@Override
	public PlanBoardDetailDto detailArticleById(String planBoardId) {
		PlanBoardDetailDto planBoardDetailDto = new PlanBoardDetailDto();
		planBoardDetailDto.setPlanBoard(planBoardMapper.detailArticleById(planBoardId));
		planBoardDetailDto.setCommentList(planBoardMapper.listCommentById(planBoardId));
		planBoardDetailDto.setTagList(planBoardMapper.listTagById(planBoardId));
		planBoardDetailDto.setLikeList(planBoardMapper.listLikeById(planBoardId));
		return planBoardDetailDto;
	}

	@Override
	public void insertArticle(PlanBoardFormDto planBoardFormDto) {
		planBoardMapper.insertArticle(planBoardFormDto.getPlanBoard());
		for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
			planBoardMapper.insertTag(tag);
		}
	}

	@Override
	public void deleteArticle(String planBoardId) {
		planBoardMapper.deleteArticle(planBoardId);
	}

	@Override
	public void modifyArticle(PlanBoardFormDto planBoardFormDto) {
		planBoardMapper.modifyArticle(planBoardFormDto.getPlanBoard());
		planBoardMapper.deleteTag(planBoardFormDto.getPlanBoard().getPlanBoardId());
		for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
			planBoardMapper.insertTag(tag);
		}
	}
	
	@Override
	public void updateHit(String planBoardId) {
		planBoardMapper.updateHit(planBoardId);
	}

	@Override
	public void insertComment(PlanCommentDto planCommentDto) {
		planBoardMapper.insertComment(planCommentDto);
	}

	@Override
	public void deleteComment(String commentId) {
		planBoardMapper.deleteComment(commentId);
	}

	@Override
	public void modifyComment(PlanCommentDto planCommentDto) {
		planBoardMapper.modifyComment(planCommentDto);
	}

	@Override
	public void insertTag(PlanBoardTagDto planBoardTagDto) {
		planBoardMapper.insertTag(planBoardTagDto);
	}

	@Override
	public void deleteTag(String planBoardTagId) {
		planBoardMapper.deleteTag(planBoardTagId);
	}

	@Override
	public void insertLike(PlanLikeDto planLikeDto) {
		planBoardMapper.insertLike(planLikeDto);
	}

	@Override
	public void deleteLike(String likeId) {
		planBoardMapper.deleteLike(likeId);
	}
	
	@Override
	public List<SidoDto> getSidoList(){
		return planBoardMapper.getSidoList();
	}
	
	@Override
	public List<GugunDto> getGugunList(String sidoCode) {
		return planBoardMapper.getGugunList(sidoCode);
	}

	@Override
	public List<AttractionInfoDto> getAttractionInfoList(Map<String, String> map){
		return planBoardMapper.getAttractionInfoList(map);
	}
	
	@Override
	public AttractionDescriptionDto getAttractionDescription(String contentId) {
		return planBoardMapper.getAttractionDescription(contentId);
	}
}
