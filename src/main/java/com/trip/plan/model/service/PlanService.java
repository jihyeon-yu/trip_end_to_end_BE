package com.trip.plan.model.service;

import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanRequestDto;

public interface PlanService {
	void createPlan(PlanRequestDto planRequestDto);
	PlanDto getPlan(String planId);
	void updatePlan(PlanDto planDto);
	void deletePlan(String planId);
}
