package com.trip.notice_board.model.service;

import java.util.List;
import java.util.Map;

import com.trip.notice_board.model.dto.NoticeBoardDto;
import com.trip.util.PageNavigation;

public interface NoticeBoardService {
	List<NoticeBoardDto> listArticle(Map<String, String> map);

	NoticeBoardDto detailArticleById(String noticeBoardId);

	void updateHit(String noticeBoardId);

	void insertArticle(NoticeBoardDto noticeBoardDto);

	void deleteArticle(String noticeBoardId);

	void modifyArticle(NoticeBoardDto noticeBoardDto);
	
	/* page navigation */
	PageNavigation makePageNavigation(Map<String, String> map);
}
