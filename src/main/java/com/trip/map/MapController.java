package com.trip.map;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan_board.model.dto.AttractionDescriptionDto;
import com.trip.plan_board.model.dto.AttractionInfoDto;
import com.trip.plan_board.model.dto.PlanBoardFileInfoDto;
import com.trip.plan_board.model.dto.GugunDto;
import com.trip.plan_board.model.dto.PlanBoardDto;
import com.trip.plan_board.model.dto.PlanBoardTagDto;
import com.trip.plan_board.model.dto.PlanCommentDto;
import com.trip.plan_board.model.dto.PlanLikeDto;
import com.trip.plan_board.model.dto.SidoDto;
import com.trip.plan_board.model.dto.TagTypeDto;
import com.trip.plan_board.model.dto.request.PlanBoardFormDto;
import com.trip.plan_board.model.dto.response.PlanBoardDetailDto;
import com.trip.plan_board.model.service.PlanBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/map", produces = "application/json; charset=utf8")
public class MapController {
	private PlanBoardService planBoardService;

	public MapController(PlanBoardService planBoardService) {
		super();
		this.planBoardService = planBoardService;
	}


	/* map */
	@GetMapping("/sido")
	public ResponseEntity<?> getSido() {
		try {
			List<SidoDto> list = planBoardService.getSidoList();
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"sidoList\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<?> getGugun(@PathVariable String sidoCode) {
		try {
			List<GugunDto> list = planBoardService.getGugunList(sidoCode);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"gugunList\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/attractioninfo")
	public ResponseEntity<?> attractionInfo(@RequestParam Map<String, String> map) {
		try {
			List<AttractionInfoDto> list = planBoardService.getAttractionInfoList(map);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"attractionInfoList\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

	@GetMapping("/attractiondescription/{contentId}")
	public ResponseEntity<?> attractionDescription(@PathVariable String contentId) {
		try {
			AttractionDescriptionDto description = planBoardService.getAttractionDescription(contentId);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body(objectMapper.writeValueAsString(description));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<?> exceptionHandling(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
