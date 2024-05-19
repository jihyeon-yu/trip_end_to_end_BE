package com.trip.notice_board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.notice_board.model.dto.NoticeBoardDto;
import com.trip.notice_board.model.service.NoticeBoardService;
import com.trip.util.PageNavigation;

@RestController
@RequestMapping(value = "/api/notice", produces = "application/json; charset=utf8")
public class NoticeBoardRestController {
	private NoticeBoardService noticeBoardService;

	public NoticeBoardRestController(NoticeBoardService noticeBoardService) {
		super();
		this.noticeBoardService = noticeBoardService;
	}

	@GetMapping("/list")
	public ResponseEntity<?> listArticle(@RequestParam Map<String, String> map) {
		try {
			if (!map.containsKey("pgno") && !map.containsKey("key") && !map.containsKey("word")) {
				map.put("pgno", "1");
				map.put("key", "");
				map.put("word", "");
			}
			List<NoticeBoardDto> list = noticeBoardService.listArticle(map);
			PageNavigation pageNavigation = noticeBoardService.makePageNavigation(map);
			ObjectMapper objectMapper = new ObjectMapper();
			String settingJson = objectMapper.writeValueAsString(map);
			String articlesJson = objectMapper.writeValueAsString(list);
			String navigationJson = objectMapper.writeValueAsString(pageNavigation);

			return ResponseEntity.ok().body("{\"setting\": " + settingJson + ", \"articles\": " + articlesJson
					+ ", \"pageNavigation\": " + navigationJson + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/{noticeId}")
	public ResponseEntity<?> detailArticleById(@PathVariable String noticeId) {
		try {
			NoticeBoardDto noticeBoardDto = noticeBoardService.detailArticleById(noticeId);
			noticeBoardService.updateHit(noticeId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body(objectMapper.writeValueAsString(noticeBoardDto));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insertArticle(@RequestBody NoticeBoardDto noticeBoard) {
		try {
			noticeBoardService.insertArticle(noticeBoard);
			return ResponseEntity.ok().body("{\"msg\" : 공지사항 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/{noticeId}")
	public ResponseEntity<?> modifyArticle(@PathVariable String noticeId, @RequestBody NoticeBoardDto noticeBoard) {
		try {
			System.out.println(noticeBoard);
			noticeBoardService.modifyArticle(noticeBoard);
			return ResponseEntity.ok().body("{\"msg\" : 공지사항 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/{noticeId}")
	public ResponseEntity<?> deleteArticle(@PathVariable String noticeId) {
		try {
			noticeBoardService.deleteArticle(noticeId);
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
