package com.trip.notice_board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trip.notice_board.model.dto.NoticeBoardDto;

@Mapper
public interface NoticeBoardMapper {
	List<NoticeBoardDto> listArticle();

	NoticeBoardDto detailArticleById(String noticeBoardId);

	void updateHit(String noticeBoardId);

	void insertArticle(NoticeBoardDto noticeBoardDto);

	void deleteArticle(String noticeBoardId);

	void modifyArticle(NoticeBoardDto noticeBoardDto);
}
