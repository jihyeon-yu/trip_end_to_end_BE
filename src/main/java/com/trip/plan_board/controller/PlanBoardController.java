package com.trip.plan_board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.plan_board.model.dto.AttractionDescriptionDto;
import com.trip.plan_board.model.dto.AttractionInfoDto;
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
@RequestMapping(value = "/api/shareplan", produces = "application/json; charset=utf8")
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
					.body(objectMapper.writeValueAsString(planBoardDetailDto));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<?> writeArticle(@RequestBody PlanBoardFormDto planBoard) {
		try {
			System.out.println(planBoard);
			planBoardService.insertArticle(planBoard);
			return ResponseEntity.ok().body("{\"msg\":" + "게시글 등록이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	@Value("${upload.dir}") // application.properties에 저장된 파일 업로드 디렉토리 경로
	private String uploadDir;

	@PostMapping("/upload/thumbnail")
	public ResponseEntity<String> handleFileUpload(@RequestParam("thumbnail") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("업로드할 파일을 선택하세요.");
		}

		try {
			// 파일 이름 중복 방지를 위해 UUID를 사용하여 고유한 파일명 생성
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

			// 저장할 디렉토리 생성 (필요시)
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
			}

			// 파일 저장 경로
			String filePath = uploadDir + File.separator + fileName;

			// 파일을 디스크에 저장
			Path path = Paths.get(filePath);
			Files.write(path, file.getBytes());

			return ResponseEntity.ok().body(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류 발생: " + e.getMessage());
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
	public ResponseEntity<?> modifyArticle(@PathVariable String planBoardId, @RequestBody PlanBoardFormDto planBoard) {
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
	public ResponseEntity<?> insertComment(@RequestBody PlanCommentDto planCommentDto) {
		try {
			planBoardService.insertComment(planCommentDto);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 등록이 완료되었습니다. }");
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
	public ResponseEntity<?> modifyComment(@RequestBody PlanCommentDto planCommentDto) {
		try {
			planBoardService.modifyComment(planCommentDto);
			return ResponseEntity.ok().body("{\"msg\":" + "댓글 수정이 완료되었습니다. }");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	/* tag - 태그 */
	@PostMapping("/insert/{planBoardId}/tag")
	public ResponseEntity<?> insertTag(@RequestBody PlanBoardTagDto planBoardTagDto) {
		try {
			planBoardService.insertTag(planBoardTagDto);
			return ResponseEntity.ok().body("{\"msg\":" + "태그 등록이 완료되었습니다. }");
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
	
	@GetMapping("/tag/{tagName}")
	public ResponseEntity<?> searchTag(@PathVariable String tagName) {
		try {
			List<TagTypeDto> list = planBoardService.searchTag(tagName);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body(objectMapper.writeValueAsString(list));
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	/* like - 좋아요 */
	@PostMapping("/insert/{planBoardId}/like")
	public ResponseEntity<?> insertLike(@RequestBody PlanLikeDto planLikeDto) {
		try {
			planBoardService.insertLike(planLikeDto);
			return ResponseEntity.ok().body("{\"msg\":" + "좋아요 등록이 완료되었습니다. }");
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
	
	/* map */
	@GetMapping("/map/sido")
	public ResponseEntity<?> getSido() {
		try {
			List<SidoDto> list = planBoardService.getSidoList();
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"sidoList\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/map/gugun/{sidoCode}")
	public ResponseEntity<?> getGugun(@PathVariable String sidoCode) {
		try {
			List<GugunDto> list = planBoardService.getGugunList(sidoCode);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"gugunList\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/map/attractioninfo")
	public ResponseEntity<?> attractionInfo(@RequestParam Map<String, String> map) {
		try {
			List<AttractionInfoDto> list = planBoardService.getAttractionInfoList(map);
			ObjectMapper objectMapper = new ObjectMapper();
			return ResponseEntity.ok().body("{\"attractionInfoList\":" + objectMapper.writeValueAsString(list) + "}");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		
	}
	
	@GetMapping("/map/attractiondescription/{contentId}")
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
