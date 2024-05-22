package com.trip.notice_board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.trip.notice_board.model.dto.NoticeBoardDto;
import com.trip.notice_board.model.mapper.NoticeBoardMapper;
import com.trip.util.PageNavigation;
import com.trip.util.SizeConstant;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {
	private NoticeBoardMapper noticeBoardMapper;

	public NoticeBoardServiceImpl(NoticeBoardMapper noticeBoardMapper) {
		super();
		this.noticeBoardMapper = noticeBoardMapper;
	}

	@Override
	public List<NoticeBoardDto> listArticle(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("key", map.get("key") == null ? "" : map.get("key"));
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		return noticeBoardMapper.listArticle(param);
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
		int totalCount = noticeBoardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);

		return pageNavigation;
	}
}
