package com.trip.qna_board.controller;

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
import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;
import com.trip.qna_board.model.dto.response.QnABoardDetailDto;
import com.trip.qna_board.model.service.QnABoardService;

@RestController
@RequestMapping(value = "/api/qna", produces = "application/json; charset=utf8")
public class QnABoardRestController {
	private QnABoardService qnaBoardService;

	public QnABoardRestController(QnABoardService qnaBoardService) {
		super();
		this.qnaBoardService = qnaBoardService;
	}

	@GetMapping("/list")
	public ResponseEntity<?> listArticle() {
		try {
			List<QnABoardDto> list = qnaBoardService.listArticle();
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"articles\":" + objectMapper.writeValueAsString(list) + "}");
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

	@PostMapping("/write")
	public ResponseEntity<?> insertArticle(QnABoardDto qnaBoardDto) {
		try {
			qnaBoardService.insertArticle(qnaBoardDto);
			return ResponseEntity.ok().body("{\"msg\" : 게시글 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/{qnaBoardId}")
	public ResponseEntity<?> modifyArticle(@PathVariable String qnaBoardId, QnABoardDto qnaBoardDto) {
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
	public ResponseEntity<?> insertComment(QnACommentDto qnaCommentDto) {
		try {
			qnaBoardService.insertComment(qnaCommentDto);
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
	public ResponseEntity<?> modifyComment(QnACommentDto qnaCommentDto) {
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
