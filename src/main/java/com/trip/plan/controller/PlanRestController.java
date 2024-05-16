package com.trip.plan.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanRequestDto;
import com.trip.plan.model.service.PlanService;

@RestController
@RequestMapping("/api/plans")
public class PlanRestController {
	  @Autowired
	    private PlanService planService;

	    @PostMapping("/create")
	    public ResponseEntity<Void> createPlan(@RequestBody PlanRequestDto planRequestDto) {
	        planService.createPlan(planRequestDto);
	        return ResponseEntity.status(HttpStatus.CREATED).build();
	    }

//	    @GetMapping("/{id}")
//	    public ResponseEntity<PlanDto> getPlan(@PathVariable int id) {
//	        PlanDto plan = planService.getPlan(id);
//	        return ResponseEntity.ok(plan);
//	    }
//
//	    @PutMapping("/{id}")
//	    public ResponseEntity<Void> updatePlan(@PathVariable int id, @RequestBody PlanDto planDto) {
//	        planService.updatePlan(id, planDto);
//	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//	    }
//
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<Void> deletePlan(@PathVariable int id) {
//	        planService.deletePlan(id);
//	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//	    }
}
