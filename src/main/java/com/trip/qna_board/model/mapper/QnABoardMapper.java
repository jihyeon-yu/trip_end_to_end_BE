package com.trip.qna_board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardListItemDto;

@Mapper
public interface QnABoardMapper {
	List<QnABoardListItemDto> listArticle(Map<String, Object> map);

	QnABoardDto detailArticleById(String qnaBoardId);

	void updateHit(String qnaBoardId);

	void insertArticle(QnABoardDto qnaBoardDto);

	void deleteArticle(String qnaBoardId);

	void modifyArticle(QnABoardDto qnaBoardDto);

	void updateStateToAnswered(String qnaBoardId);

	void updateStateToUnAnswered(String qnaBoardId);

	/* comment */
	List<QnACommentDto> listComment(String qnaBoardId);

	void insertComment(QnACommentDto qnaCommentDto);

	void deleteComment(String commentId);

	void modifyComment(QnACommentDto qnaCommentDto);

	/* page Navigation */
	int getTotalArticleCount(Map<String, Object> param);
}
