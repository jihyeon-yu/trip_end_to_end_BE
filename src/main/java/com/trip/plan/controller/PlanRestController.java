package com.trip.plan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanRequestDto;
import com.trip.plan.model.service.PlanService;

@RestController
@RequestMapping(value = "/api/plans", produces = "application/json; charset=utf8")
public class PlanRestController {
	@Autowired
	private PlanService planService;

	@PostMapping("/create")
	public ResponseEntity<Void> createPlan(@RequestBody PlanRequestDto planRequestDto) {
		planService.createPlan(planRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("list/{id}")
	public ResponseEntity<List<PlanDto>> getPlanListByMember(@PathVariable String id) {
		String memberId = planService.getMemberIdById(id);
		List<PlanDto> plan = planService.getPlanListByMember(memberId);
		return ResponseEntity.ok(plan);
	}

	@GetMapping("detail/{planId}")
	public ResponseEntity<PlanRequestDto> getPlanDetailByMember(@PathVariable String planId) {
		PlanRequestDto plan = planService.getPlanDetailByPlanId(planId);
		System.out.println(plan.getPaymentDetails());
		return ResponseEntity.ok(plan);
	}

	@DeleteMapping("/delete/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable String planId) {
		planService.deletePlan(planId);
		return ResponseEntity.ok("삭제가 완료되었습니다.");
	}
	
	@PutMapping("/update/{planId}")
	public ResponseEntity<String> updatePlan(@RequestBody PlanRequestDto planRequestDto) {
		planService.updatePlan(planRequestDto);
		return ResponseEntity.ok("수정이 완료되었습니다.");
	}
	

}
