package com.trip.plan_board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
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
		planBoardDetailDto.setPlanBoard(planBoardMapper.getArticleById(planBoardId));
		planBoardDetailDto.setCommentList(planBoardMapper.listCommentById(planBoardId));
		planBoardDetailDto.setTagList(planBoardMapper.listTagById(planBoardId));
		planBoardDetailDto.setLikeList(planBoardMapper.listLikeById(planBoardId));
		return planBoardDetailDto;
	}

	@Override
	public void insertArticle(PlanBoardFormDto planBoardFormDto) {
		planBoardMapper.insertArticle(planBoardFormDto.getPlanBoard());
		for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
			planBoardMapper.insertArticleTag(tag);
		}
	}

	@Override
	public void deleteArticle(String planBoardId) {
		// TODO: plan-board delete 요청 -> on cascade 처리?
		planBoardMapper.deleteArticle(planBoardId);
	}

	@Override
	public void modifyArticle(PlanBoardFormDto planBoardFormDto) {
		planBoardMapper.modifyArticle(planBoardFormDto.getPlanBoard());
		// TODO: tag 수정 추가하기 
	}

}
