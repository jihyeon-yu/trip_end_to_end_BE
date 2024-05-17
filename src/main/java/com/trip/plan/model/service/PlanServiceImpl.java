package com.trip.plan.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.plan.model.dto.BookGroupDto;
import com.trip.plan.model.dto.PaymentDetailDto;
import com.trip.plan.model.dto.PlanDto;
import com.trip.plan.model.dto.PlanGroupDto;
import com.trip.plan.model.dto.PlanLocationDto;
import com.trip.plan.model.dto.PlanRequestDto;
import com.trip.plan.model.dto.PlanScheduleDto;
import com.trip.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanMapper planMapper;

	@Override
	public void createPlan(PlanRequestDto planRequestDto) {
		planMapper.insertPlan(planRequestDto.getPlanDto());
		String planId = planMapper.searchLastPlanId();
		// 참여 맴버 추가
		if (planRequestDto.getMemberIds().size() != 0) {
			for (PlanGroupDto planGroupDto : planRequestDto.getMemberIds()) {
				planGroupDto.setPlanId(planId);
				planMapper.insertPlanMember(planGroupDto);
			}
		}
		// 여행 기간별 일자 추가
		if (planRequestDto.getScheduleDates().size() != 0) {
			for (PlanScheduleDto planScheduleDto : planRequestDto.getScheduleDates()) {
				planScheduleDto.setPlanId(planId);
				planMapper.insertPlanSchedule(planScheduleDto);
			}
		}
		// 일자별 방문 장소 추가
		if (planRequestDto.getPlanLocations().size() != 0) {
			String planScheduleId = planMapper.searchLastPlanScheduleId();
			for (List<PlanLocationDto> planLocationList : planRequestDto.getPlanLocations()) {
				for (PlanLocationDto planLocation : planLocationList) {
					planLocation.setPlanScheduleId(planScheduleId);
					planMapper.insertPlanLocation(planLocation);
				}
			}
		}
		// 예약 내역 추가
		if (planRequestDto.getBookContents().size() != 0) {
			for (BookGroupDto bookGroupDto : planRequestDto.getBookContents()) {
				bookGroupDto.setPlanId(planId);
				planMapper.insertBookDetail(bookGroupDto);
			}
		}
		// 결제 내역 추가
		if (planRequestDto.getPaymentDetails().size() != 0) {
			for (PaymentDetailDto paymentDetailDto : planRequestDto.getPaymentDetails()) {
				paymentDetailDto.setPlanId(planId);
				planMapper.insertPaymentDetail(paymentDetailDto);
			}
		}
	}

	@Override
	public List<PlanDto> getPlanListByMember(String memberId) {
		return planMapper.searchPlanList(memberId);
	}

	@Override
	public PlanRequestDto getPlanDetailByPlanId(String planId) {
		PlanRequestDto plan = new PlanRequestDto();
		PlanDto planDto = planMapper.searchPlanByPlanId(planId);
		// plan 정보 조회
		plan.setPlanDto(planDto);
		// 참여 맴버 조회
		plan.setMemberIds(planMapper.searchPlanGroup(planId));
		// 예약 내역 조회
		plan.setBookContents(planMapper.searchBookGroup(planId));
		// 여행 기간별 일자 조회
		List<PlanScheduleDto> planSchedule = planMapper.searchPlanSchedule(planId);
		plan.setScheduleDates(planSchedule);
		// 일자별 방문 장소 조회
		List<List<PlanLocationDto>> location = new ArrayList<>();
		for (PlanScheduleDto planScheduleDto : planSchedule) {
			String planScheduleId = planScheduleDto.getPlanScheduleId();
			location.add(planMapper.searchPlanLocation(planScheduleId));
		}
		plan.setPlanLocations(location);
		// 결제 내역 조회
		plan.setPaymentDetails(planMapper.searchPaymentDetail(planId));
		return plan;
	}

	@Override
	public void deletePlan(String planId) {
		planMapper.deletePlan(planId);
	}

	@Override
	public void updatePlan(PlanRequestDto planRequestDto) {
		// TODO Auto-generated method stub
		
	}

}
