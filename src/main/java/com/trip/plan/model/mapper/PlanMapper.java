package com.trip.plan.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.trip.plan.model.dto.BookGroupDto;
import com.trip.plan.model.dto.PaymentDetailDto;
import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanGroupDto;
import com.trip.plan.model.dto.PlanLocationDto;
import com.trip.plan.model.dto.PlanRequestDto;
import com.trip.plan.model.dto.PlanScheduleDto;

@Mapper
public interface PlanMapper {
	void insertPlan(PlanDto planDto);
	
	List<PlanDto> searchPlanList(String memberId);
	
	PlanDto searchPlanByPlanId(String planId);
	
	List<PlanGroupDto> searchPlanGroup(String planId);
	
	List<BookGroupDto> searchBookGroup(String planId);
	
	List<PaymentDetailDto> searchPaymentDetail(String planId);
	
	List<PlanScheduleDto> searchPlanSchedule(String planId);
	
	List<PlanLocationDto> searchPlanLocation(String planScheduleId);

	String searchLastPlanId();
	
	void updatePlan(PlanDto planDto);
	
	void updatePlanMember(PlanGroupDto planGroupDto);
	
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
