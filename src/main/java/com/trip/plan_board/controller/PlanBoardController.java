package com.trip.plan_board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.request.PlanBoardDetailRequestDto;
import com.trip.plan_board.model.service.PlanBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/detail")
	public ResponseEntity<?> detailArticleById() {
		try {
			String planBoardId = "5";
			PlanBoardDetailRequestDto planBoardDetailRequestDto = planBoardService.detailArticleById(planBoardId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"article\":" + objectMapper.writeValueAsString(planBoardDetailRequestDto) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
