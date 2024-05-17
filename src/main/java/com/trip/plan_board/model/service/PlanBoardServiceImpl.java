package com.trip.plan_board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.plan_board.model.dto.PlanBoardDto;
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

}
