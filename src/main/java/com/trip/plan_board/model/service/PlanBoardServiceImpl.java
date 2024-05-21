package com.trip.plan_board.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	private PlanBoardMapper planBoardMapper;

	@Value("${upload.dir}") // application.properties에 저장된 파일 업로드 디렉토리 경로
	private String uploadDir;

	public PlanBoardServiceImpl(PlanBoardMapper planBoardMapper) {
		super();
		this.planBoardMapper = planBoardMapper;
	}

	@Override
	public List<PlanBoardDto> listArticle() {
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
			planBoardMapper.insertArticle(planBoard);
			String planBoardId = planBoard.getPlanBoardId();
			String fileName = generateImageUrl(file);

			// 파일 업로드
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			PlanBoardFileInfoDto fileInfoDto = new PlanBoardFileInfoDto();
			fileInfoDto.setPlanBoardId(planBoardId);
			fileInfoDto.setSaveFolder(uploadDir); // 파일을 저장할 경로
			fileInfoDto.setOriginalFile(file.getOriginalFilename()); // 원래 파일 이름
			fileInfoDto.setSaveFile(fileName); // 저장된 파일 이름 (plan_board의 thumbnail 필드 값)
			planBoardMapper.registerFile(fileInfoDto); // 파일 정보를 데이터베이스에 저장

			// 태그 삽입
			for (PlanBoardTagDto tag : planBoardFormDto.getTagList()) {
				tag.setPlanBoardId(planBoardId);
				planBoardMapper.insertTag(tag);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	private String generateImageUrl(MultipartFile file) throws IOException {
		if (file.isEmpty())
			throw new IllegalArgumentException("File is Empty");
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		File targetFile = new File(uploadDir, fileName);
		file.transferTo(targetFile);
		return fileName;
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
		planBoardMapper.modifyArticle(planBoardFormDto.getPlanBoard());
		try {
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			String fileName = generateImageUrl(file);
			PlanBoardFileInfoDto fileInfoDto = new PlanBoardFileInfoDto();
			fileInfoDto.setPlanBoardId(planBoardFormDto.getPlanBoard().getPlanBoardId());
			fileInfoDto.setSaveFolder(uploadDir);
			fileInfoDto.setOriginalFile(file.getOriginalFilename());
			fileInfoDto.setSaveFile(fileName);
			PlanBoardFileInfoDto isExisted = planBoardMapper.fileInfo(planBoardFormDto.getPlanBoard().getPlanBoardId());
			if (isExisted instanceof PlanBoardFileInfoDto)
				planBoardMapper.updateFile(fileInfoDto);
			else
				planBoardMapper.registerFile(fileInfoDto);
		} catch(IOException e) {
			e.printStackTrace();
		}
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
