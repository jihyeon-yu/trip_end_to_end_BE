package com.trip.plan_board.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;
import com.trip.plan_board.model.dto.request.PlanBoardFormDto;
import com.trip.plan_board.model.dto.response.PlanBoardDetailDto;
import com.trip.plan_board.model.service.PlanBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(value = "/api/shareplan", produces = "application/json; charset=utf8")
@CrossOrigin("*")
public class PlanBoardController {
	private PlanBoardService planBoardService;

	public PlanBoardController(PlanBoardService planBoardService) {
		super();
		this.planBoardService = planBoardService;
	}

	/* article - 게시글 */
	@GetMapping("/list")
	public ResponseEntity<?> listArticle() {
		try {
			List<PlanBoardDto> list = planBoardService.listArticle();
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"articles\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/{planBoardId}")
	public ResponseEntity<?> detailArticleById(@PathVariable String planBoardId) {
		try {
			PlanBoardDetailDto planBoardDetailDto = planBoardService.detailArticleById(planBoardId);
			planBoardService.updateHit(planBoardId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok()
					.body("{\"article\":" + objectMapper.writeValueAsString(planBoardDetailDto) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> writeArticle(PlanBoardFormDto planBoard) {
		try {
			planBoardService.insertArticle(planBoard);
			return ResponseEntity.ok().body("{\"msg\":" + "게시글 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/{planBoardId}")
	public ResponseEntity<?> deleteArticle(@PathVariable String planBoardId) {
		try {
			planBoardService.deleteArticle(planBoardId);
			return ResponseEntity.ok().body("{\"msg\":" + "게시글 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/{planBoardId}")
	public ResponseEntity<?> modifyArticle(@PathVariable String planBoardId, PlanBoardFormDto planBoard) {
		try {
			if (!planBoardId.equals(planBoard.getPlanBoard().getPlanBoardId())) {
				return ResponseEntity.badRequest().body("{\"msg\":" + "잘못된 요청입니다. }");
			}
			planBoardService.modifyArticle(planBoard);
			return ResponseEntity.ok().body("{\"msg\":" + "게시글 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	/* comment - 댓글 */
	@PostMapping("/insert/{planBoardId}/comment")
	public ResponseEntity<?> insertComment(PlanCommentDto planCommentDto) {
		try {
			planBoardService.insertComment(planCommentDto);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 추가가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable String commentId) {
		try {
			planBoardService.deleteComment(commentId);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/comment/{commentId}")
	public ResponseEntity<?> modifyComment(PlanCommentDto planCommentDto) {
		try {
			planBoardService.modifyComment(planCommentDto);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	/* tag - 태그 */
	@PostMapping("/insert/{planBoardId}/tag")
	public ResponseEntity<?> insertTag(PlanBoardTagDto planBoardTagDto) {
		try {
			planBoardService.insertTag(planBoardTagDto);
			return ResponseEntity.ok().body("{\"msg\":" + "태그 추가가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/tag/{planBoardTagId}")
	public ResponseEntity<?> deleteTag(@PathVariable String planBoardTagId) {
		try {
			planBoardService.deleteTag(planBoardTagId);
			return ResponseEntity.ok().body("{\"msg\":" + "태그 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	/* like - 좋아요 */
	@PostMapping("/insert/{planBoardId}/like")
	public ResponseEntity<?> insertLike(PlanLikeDto planLikeDto) {
		try {
			planBoardService.insertLike(planLikeDto);
			return ResponseEntity.ok().body("{\"msg\":" + "좋아요 추가가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/like/{planLikeId}")
	public ResponseEntity<?> deleteLike(@PathVariable String planLikeId) {
		try {
			planBoardService.deleteLike(planLikeId);
			return ResponseEntity.ok().body("{\"msg\":" + "좋아요 삭제가 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
