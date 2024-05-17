package com.trip.qna_board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardDetailDto;
import com.trip.qna_board.model.mapper.QnABoardMapper;

@Service
public class QnABoardServiceImpl implements QnABoardService {
	private QnABoardMapper qnaBoardMapper;

	public QnABoardServiceImpl(QnABoardMapper qnaBoardMapper) {
		super();
		this.qnaBoardMapper = qnaBoardMapper;
	}

	@Override
	public List<QnABoardDto> listArticle() {
		return qnaBoardMapper.listArticle();
	}

	@Override
	public QnABoardDetailDto detailArticleById(String qnaBoardId) {
		QnABoardDetailDto qnaBoardDetailDto = new QnABoardDetailDto();
		qnaBoardDetailDto.setQnaBoardDto(qnaBoardMapper.detailArticleById(qnaBoardId));
		qnaBoardDetailDto.setCommentList(qnaBoardMapper.listComment(qnaBoardId));
		return qnaBoardDetailDto;
	}

	@Override
	public void updateHit(String qnaBoardId) {
		qnaBoardMapper.updateHit(qnaBoardId);
	}

	@Override
	public void insertArticle(QnABoardDto qnaBoardDto) {
		qnaBoardMapper.insertArticle(qnaBoardDto);
	}

	@Override
	public void deleteArticle(String qnaBoardId) {
		qnaBoardMapper.deleteArticle(qnaBoardId);
	}

	@Override
	public void modifyArticle(QnABoardDto qnaBoardDto) {
		qnaBoardMapper.modifyArticle(qnaBoardDto);
	}

	@Override
	public void insertComment(QnACommentDto qnaCommentDto) {
		qnaBoardMapper.insertComment(qnaCommentDto);
	}

	@Override
	public void deleteComment(String commentId) {
		qnaBoardMapper.deleteComment(commentId);
	}

	@Override
	public void modifyComment(QnACommentDto qnaCommentDto) {
		qnaBoardMapper.modifyComment(qnaCommentDto);
	}
	
}
