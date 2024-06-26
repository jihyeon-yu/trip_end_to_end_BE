package com.trip.plan.model.service;

import java.util.List;

import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanRequestDto;

public interface PlanService {
	void createPlan(PlanRequestDto planRequestDto);
	List<PlanDto> getPlanListByMember(String memberId);
	List<PlanDto> getAllPlanListByMember(String memberId);
	PlanRequestDto getPlanDetailByPlanId(String planId);
	void updatePlan(PlanRequestDto planRequestDto);
	void deletePlan(String planId);
	String getMemberIdById(String id);
}
