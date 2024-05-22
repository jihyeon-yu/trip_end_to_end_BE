package com.trip.qna_board.controller;

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
import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardDetailDto;
import com.trip.qna_board.model.dto.response.QnABoardListItemDto;
import com.trip.qna_board.model.service.QnABoardService;
import com.trip.util.PageNavigation;

@RestController
@RequestMapping(value = "/api/qna", produces = "application/json; charset=utf8")
public class QnABoardRestController {
	private QnABoardService qnaBoardService;

	public QnABoardRestController(QnABoardService qnaBoardService) {
		super();
		this.qnaBoardService = qnaBoardService;
	}

	@GetMapping("/list")
	public ResponseEntity<?> listArticle(@RequestParam Map<String, String> map) {
		try {
			if (!map.containsKey("pgno") && !map.containsKey("key") && !map.containsKey("word")) {
				map.put("pgno", "1");
				map.put("key", "");
				map.put("word", "");
			}
			List<QnABoardListItemDto> list = qnaBoardService.listArticle(map);
			PageNavigation pageNavigation = qnaBoardService.makePageNavigation(map);
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

	@GetMapping("/{qnaBoardId}")
	public ResponseEntity<?> detailArticleById(@PathVariable String qnaBoardId) {
		try {
			QnABoardDetailDto qnaBoardDetailDto = qnaBoardService.detailArticleById(qnaBoardId);
			qnaBoardService.updateHit(qnaBoardId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"article\":" + objectMapper.writeValueAsString(qnaBoardDetailDto) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insertArticle(@RequestBody QnABoardDto qnaBoardDto) {
		try {
			qnaBoardService.insertArticle(qnaBoardDto);
			return ResponseEntity.ok().body("{\"msg\" : 게시글 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/{qnaBoardId}")
	public ResponseEntity<?> modifyArticle(@PathVariable String qnaBoardId, @RequestBody QnABoardDto qnaBoardDto) {
		try {
			qnaBoardService.modifyArticle(qnaBoardDto);
			return ResponseEntity.ok().body("{\"msg\" : 게시글 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/{qnaBoardId}")
	public ResponseEntity<?> deleteArticle(@PathVariable String qnaBoardId) {
		try {
			qnaBoardService.deleteArticle(qnaBoardId);
			return ResponseEntity.ok().body("{\"msg\" : 게시글 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	/* comment - 댓글 */
	@PostMapping("/insert/{qnaBoardId}/comment")
	public ResponseEntity<?> insertComment(@RequestBody QnACommentDto qnaCommentDto) {
		try {
			qnaBoardService.insertComment(qnaCommentDto);
			qnaBoardService.updateStateToAnswered(qnaCommentDto.getQnaBoardId());
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
		try {
			qnaBoardService.deleteComment(commentId);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/comment/{commentId}")
	public ResponseEntity<?> modifyComment(@RequestBody QnACommentDto qnaCommentDto) {
		try {
			qnaBoardService.modifyComment(qnaCommentDto);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
