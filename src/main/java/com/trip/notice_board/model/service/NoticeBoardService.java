package com.trip.notice_board.model.service;

import java.util.List;

import com.trip.notice_board.model.dto.NoticeBoardDto;

public interface NoticeBoardService {
	List<NoticeBoardDto> listArticle();
	NoticeBoardDto detailArticleById(String noticeBoardId);
	void updateHit(String noticeBoardId);
	void insertArticle(NoticeBoardDto noticeBoardDto);
	void deleteArticle(String noticeBoardId);
	void modifyArticle(NoticeBoardDto noticeBoardDto);
}
