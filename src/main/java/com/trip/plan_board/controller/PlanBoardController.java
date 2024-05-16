package com.trip.plan_board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan_board.model.dto.PlanBoardDto;
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
			// TODO: hit column 추가 
			// planBoardService.updateHit(planBoardId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"article\":" + objectMapper.writeValueAsString(planBoardDetailDto) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/write")
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
			planBoardService.modifyArticle(planBoard);
			return ResponseEntity.ok().body("{\"msg\":" + "게시글 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
//  TODO: comment CRUD 추가  
//	@PostMapping("/write/comment/{planBoardId}")
//	public ResponseEntity<?> insertComment() {
//		try {
//			
//		} catch(Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
