package com.trip.qna_board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardDetailDto;
import com.trip.qna_board.model.dto.response.QnABoardListItemDto;
import com.trip.qna_board.model.mapper.QnABoardMapper;
import com.trip.util.PageNavigation;
import com.trip.util.SizeConstant;

@Service
public class QnABoardServiceImpl implements QnABoardService {
	private QnABoardMapper qnaBoardMapper;

	public QnABoardServiceImpl(QnABoardMapper qnaBoardMapper) {
		super();
		this.qnaBoardMapper = qnaBoardMapper;
	}

	@Override
	public List<QnABoardListItemDto> listArticle(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", map.get("key") == null ? "" : map.get("key"));
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		List<QnABoardListItemDto> list = qnaBoardMapper.listArticle(param);
		for (QnABoardListItemDto article : list) {
			List<QnACommentDto> commentList = qnaBoardMapper.listComment(article.getQnaBoardId());
			if (commentList.size() == 0) {
				qnaBoardMapper.updateStateToUnAnswered(article.getQnaBoardId());
				article.setIsAnswered("0");
			}
			else {
				qnaBoardMapper.updateStateToAnswered(article.getQnaBoardId());
				article.setIsAnswered("1");
			}
		}
		return list;
	}

	@Override
	public QnABoardDetailDto detailArticleById(String qnaBoardId) {
		QnABoardDetailDto qnaBoardDetailDto = new QnABoardDetailDto();
		qnaBoardDetailDto.setQnaBoardDto(qnaBoardMapper.detailArticleById(qnaBoardId));
		List<QnACommentDto> commentList = qnaBoardMapper.listComment(qnaBoardId);
		if (commentList.size() == 0) {
			qnaBoardMapper.updateStateToUnAnswered(qnaBoardDetailDto.getQnaBoardDto().getQnaBoardId());
			qnaBoardDetailDto.getQnaBoardDto().setIsAnswered("0");
		}
		else {
			qnaBoardMapper.updateStateToAnswered(qnaBoardDetailDto.getQnaBoardDto().getQnaBoardId());
			qnaBoardDetailDto.getQnaBoardDto().setIsAnswered("1");
		}
		qnaBoardDetailDto.setCommentList(commentList);
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
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = qnaBoardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);

		return pageNavigation;
	}

	@Override
	public void updateStateToAnswered(String qnaBoardId) {
		qnaBoardMapper.updateStateToAnswered(qnaBoardId);
	}

	@Override
	public void updateStateToUnAnswered(String qnaBoardId) {
		qnaBoardMapper.updateStateToUnAnswered(qnaBoardId);
	}

	
}
