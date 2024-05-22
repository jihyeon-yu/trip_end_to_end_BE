package com.trip.qna_board.model.service;

import java.util.List;
import java.util.Map;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardDetailDto;
import com.trip.qna_board.model.dto.response.QnABoardListItemDto;
import com.trip.util.PageNavigation;

public interface QnABoardService {
	List<QnABoardListItemDto> listArticle(Map<String, String> map);

	QnABoardDetailDto detailArticleById(String qnaBoardId);

	void updateHit(String qnaBoardId);

	void insertArticle(QnABoardDto qnaBoardDto);

	void deleteArticle(String qnaBoardId);

	void modifyArticle(QnABoardDto qnaBoardDto);

	void updateStateToAnswered(String qnaBoardId);

	void updateStateToUnAnswered(String qnaBoardId);

	/* comment */
	void insertComment(QnACommentDto qnaCommentDto);

	void deleteComment(String commentId);

	void modifyComment(QnACommentDto qnaCommentDto);

	/* page navigation */
	PageNavigation makePageNavigation(Map<String, String> map);
}
