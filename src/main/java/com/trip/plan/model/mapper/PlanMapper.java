package com.trip.plan.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.trip.plan.model.dto.BookGroupDto;
import com.trip.plan.model.dto.PaymentDetailDto;
import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanGroupDto;
import com.trip.plan.model.dto.PlanLocationDto;
import com.trip.plan.model.dto.PlanScheduleDto;

@Mapper
public interface PlanMapper {
	void insertPlan(PlanDto planDto);
	
	PlanDto searchPlanById(String planId);
	
	String searchLastPlanId();
	
	void updatePlan(PlanDto planDto);
	
	void deletePlan(String planId);
	
	void insertPlanMember(PlanGroupDto planGroupDto);
	
	void deletePlanMember(String planId);
	
	void insertPlanSchedule(PlanScheduleDto schedule);
	
	void deletePlanSchedule(String planId);
	
	String searchLastPlanScheduleId();
	
	void insertPlanLocation(PlanLocationDto location);
	
	void deletePlanLocation(String planScheduleId);
	
	void insertBookDetail(BookGroupDto bookGroupDto);
	
	void deleteBookDetail(String planId);
	
	void insertPaymentDetail(PaymentDetailDto paymentDetailDto);
	
	void deletePaymentDetail(String planId);
	
}
