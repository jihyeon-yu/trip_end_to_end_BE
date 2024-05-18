package com.trip.notice_board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.notice_board.model.dto.NoticeBoardDto;
import com.trip.notice_board.model.mapper.NoticeBoardMapper;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {
	private NoticeBoardMapper noticeBoardMapper;

	public NoticeBoardServiceImpl(NoticeBoardMapper noticeBoardMapper) {
		super();
		this.noticeBoardMapper = noticeBoardMapper;
	}

	@Override
	public List<NoticeBoardDto> listArticle() {
		return noticeBoardMapper.listArticle();
	}

	@Override
	public NoticeBoardDto detailArticleById(String noticeBoardId) {
		return noticeBoardMapper.detailArticleById(noticeBoardId);
	}

	@Override
	public void updateHit(String noticeBoardId) {
		noticeBoardMapper.updateHit(noticeBoardId);
	}

	@Override
	public void insertArticle(NoticeBoardDto noticeBoardDto) {
		noticeBoardMapper.insertArticle(noticeBoardDto);
	}

	@Override
	public void deleteArticle(String noticeBoardId) {
		noticeBoardMapper.deleteArticle(noticeBoardId);
	}

	@Override
	public void modifyArticle(NoticeBoardDto noticeBoardDto) {
		noticeBoardMapper.modifyArticle(noticeBoardDto);
	}
}
