package com.trip.plan_board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.request.PlanBoardDetailRequestDto;
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
	public PlanBoardDetailRequestDto detailArticleById(String planBoardId) {
		PlanBoardDetailRequestDto planBoardDetailRequestDto = new PlanBoardDetailRequestDto();
		planBoardDetailRequestDto.setPlanBoard(planBoardMapper.getArticleById(planBoardId));
		planBoardDetailRequestDto.setCommentList(planBoardMapper.listCommentById(planBoardId));
		planBoardDetailRequestDto.setTagList(planBoardMapper.listTagById(planBoardId));
		planBoardDetailRequestDto.setLikeList(planBoardMapper.listLikeById(planBoardId));
		return planBoardDetailRequestDto;
	}

}
