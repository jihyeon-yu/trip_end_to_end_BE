package com.trip.qna_board.model.service;

import java.util.List;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardDetailDto;

public interface QnABoardService {
	List<QnABoardDto> listArticle();

	QnABoardDetailDto detailArticleById(String qnaBoardId);

	void updateHit(String qnaBoardId);

	void insertArticle(QnABoardDto qnaBoardDto);

	void deleteArticle(String qnaBoardId);

	void modifyArticle(QnABoardDto qnaBoardDto);
	
	/* comment */
	void insertComment(QnACommentDto qnaCommentDto);

	void deleteComment(String commentId);

	void modifyComment(QnACommentDto qnaCommentDto);
}
