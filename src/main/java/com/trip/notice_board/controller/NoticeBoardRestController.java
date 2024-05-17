package com.trip.notice_board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.notice_board.model.dto.NoticeBoardDto;
import com.trip.notice_board.model.service.NoticeBoardService;

@RestController
@RequestMapping(value = "/api/notice", produces = "application/json; charset=utf8")
public class NoticeBoardRestController {
	private NoticeBoardService noticeBoardService;

	public NoticeBoardRestController(NoticeBoardService noticeBoardService) {
		super();
		this.noticeBoardService = noticeBoardService;
	}

	@GetMapping("/list")
	public ResponseEntity<?> listArticle() {
		try {
			List<NoticeBoardDto> list = noticeBoardService.listArticle();
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"articles\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/{noticeId}")
	public ResponseEntity<?> detailArticleById(@PathVariable String noticeId) {
		try {
			NoticeBoardDto noticeBoardDto = noticeBoardService.detailArticleById(noticeId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"article\":" + objectMapper.writeValueAsString(noticeBoardDto) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/write")
	public ResponseEntity<?> insertArticle(NoticeBoardDto noticeBoard) {
		try {
			noticeBoardService.insertArticle(noticeBoard);
			return ResponseEntity.ok().body("{\"msg\" : 공지사항 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/{noticeId}")
	public ResponseEntity<?> modifyArticle(@PathVariable String noticeId, NoticeBoardDto noticeBoard) {
		try {
			noticeBoardService.modifyArticle(noticeBoard);
			return ResponseEntity.ok().body("{\"msg\" : 공지사항 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/{noticeId}")
	public ResponseEntity<?> deleteArticle(@PathVariable String noticeBoard) {
		try {
			noticeBoardService.deleteArticle(noticeBoard);
			return ResponseEntity.ok().body("{\"msg\" : 공지사항 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
