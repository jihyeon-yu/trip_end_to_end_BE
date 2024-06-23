package com.trip.plan_board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.trip.plan_board.model.dto.AttractionDescriptionDto;
import com.trip.plan_board.model.dto.AttractionInfoDto;
import com.trip.plan_board.model.dto.PlanBoardFileInfoDto;
import com.trip.plan_board.model.dto.GugunDto;
import com.trip.plan_board.model.dto.SidoDto;
import com.trip.plan_board.model.dto.TagTypeDto;
import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;
import com.trip.plan_board.model.dto.request.PlanBoardFormDto;
import com.trip.plan_board.model.dto.response.PlanBoardDetailDto;
import com.trip.plan_board.model.mapper.PlanBoardMapper;

@Service
public class PlanBoardServiceImpl implements PlanBoardService {
	@Autowired
	private PlanBoardMapper planBoardMapper;
	@Autowired
    private AmazonS3 s3Client;
    @Value("${aws.s3.bucketName}")
    private String bucketName;

	public PlanBoardServiceImpl(PlanBoardMapper planBoardMapper) {
		super();
		this.planBoardMapper = planBoardMapper;
	}

	@Override
	public List<PlanBoardDto> listArticle() {
		List<PlanBoardDto> list = planBoardMapper.listArticle();
		for (PlanBoardDto planBoardDto : list) {
			int likeCnt = planBoardMapper.listLikeById(planBoardDto.getPlanBoardId()).size();
			planBoardDto.setLikeCnt(likeCnt) ;
		}
		return planBoardMapper.listArticle();
	}

	@Override
	public PlanBoardDetailDto detailArticleById(String planBoardId) {
		PlanBoardDetailDto planBoardDetailDto = new PlanBoardDetailDto();
		planBoardDetailDto.setPlanBoard(planBoardMapper.detailArticleById(planBoardId));
		planBoardDetailDto.setCommentList(planBoardMapper.listCommentById(planBoardId));
		planBoardDetailDto.setTagList(planBoardMapper.listTagById(planBoardId));
		planBoardDetailDto.setLikeList(planBoardMapper.listLikeById(planBoardId));

		return planBoardDetailDto;
	}

	@Override
	public void insertArticle(PlanBoardFormDto planBoardFormDto, MultipartFile file) {
		try {
			// 게시글 정보 업데이트
			PlanBoardDto planBoard = planBoardFormDto.getPlanBoard();
			String planBoardId = planBoard.getPlanBoardId();
			
			// 파일 업로드
			planBoard.setThumbnail(uploadFile(file));
			
			planBoardMapper.insertArticle(planBoard);
			
			// 태그 삽입
			for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
				tag.setPlanBoardId(planBoardId);
				planBoardMapper.insertTag(tag);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        return s3Client.getUrl(bucketName, fileName).toString();
    }
	
	@Override
	public void insertArticle(PlanBoardFormDto planBoardFormDto) {
		// 게시글 정보 업데이트
		PlanBoardDto planBoard = planBoardFormDto.getPlanBoard();
		planBoardMapper.insertArticle(planBoard);
		String planBoardId = planBoard.getPlanBoardId();

		// 태그 삽입
		for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
			tag.setPlanBoardId(planBoardId);
			planBoardMapper.insertTag(tag);
		}
	}

	@Override
	public PlanBoardFileInfoDto fileInfo(String planBoardId) {
		return planBoardMapper.fileInfo(planBoardId);
	}

	@Override
	public void deleteArticle(String planBoardId) {
		planBoardMapper.deleteArticle(planBoardId);
	}

	@Override
	public void modifyArticle(PlanBoardFormDto planBoardFormDto, MultipartFile file) {
		try {
			planBoardFormDto.getPlanBoard().setThumbnail(uploadFile(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		planBoardMapper.modifyArticle(planBoardFormDto.getPlanBoard());
		
		planBoardMapper.deleteTag(planBoardFormDto.getPlanBoard().getPlanBoardId());
		for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
			tag.setPlanBoardId(planBoardFormDto.getPlanBoard().getPlanBoardId());
			planBoardMapper.insertTag(tag);
		}
	}
	
	@Override
	public void modifyArticle(PlanBoardFormDto planBoardFormDto) {
		planBoardMapper.modifyArticle(planBoardFormDto.getPlanBoard());
		planBoardMapper.deleteTag(planBoardFormDto.getPlanBoard().getPlanBoardId());
		for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
			tag.setPlanBoardId(planBoardFormDto.getPlanBoard().getPlanBoardId());
			planBoardMapper.insertTag(tag);
		}
	}

	@Override
	public void updateHit(String planBoardId) {
		planBoardMapper.updateHit(planBoardId);
	}

	@Override
	public void insertComment(PlanCommentDto planCommentDto) {
		planBoardMapper.insertComment(planCommentDto);
	}

	@Override
	public void deleteComment(String commentId) {
		planBoardMapper.deleteComment(commentId);
	}

	@Override
	public void modifyComment(PlanCommentDto planCommentDto) {
		planBoardMapper.modifyComment(planCommentDto);
	}

	@Override
	public void insertTag(PlanBoardTagDto planBoardTagDto) {
		planBoardMapper.insertTag(planBoardTagDto);
	}

	@Override
	public void deleteTag(String planBoardTagId) {
		planBoardMapper.deleteTag(planBoardTagId);
	}

	@Override
	public List<TagTypeDto> searchTag(String tagName) {
		return planBoardMapper.searchTag(tagName);
	}

	@Override
	public void insertLike(PlanLikeDto planLikeDto) {
		planBoardMapper.insertLike(planLikeDto);
	}

	@Override
	public void deleteLike(String likeId) {
		planBoardMapper.deleteLike(likeId);
	}

	@Override
	public List<SidoDto> getSidoList() {
		return planBoardMapper.getSidoList();
	}

	@Override
	public List<GugunDto> getGugunList(String sidoCode) {
		return planBoardMapper.getGugunList(sidoCode);
	}

	@Override
	public List<AttractionInfoDto> getAttractionInfoList(Map<String, String> map) {
		return planBoardMapper.getAttractionInfoList(map);
	}

	@Override
	public AttractionDescriptionDto getAttractionDescription(String contentId) {
		return planBoardMapper.getAttractionDescription(contentId);
	}

	@Override
	public List<PlanBoardTagDto> listTagById(String planBoardId) {
		return planBoardMapper.listTagById(planBoardId);
	}

	@Override
	public List<PlanCommentDto> listCommentById(String planBoardId) {
		return planBoardMapper.listCommentById(planBoardId);
	}

	@Override
	public List<PlanLikeDto> listLikeById(String planBoardId) {
		return planBoardMapper.listLikeById(planBoardId);
	}

}
