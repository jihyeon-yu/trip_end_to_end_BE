package com.trip.plan.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.member.model.mapper.MemberMapper;
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

	@Autowired
	private MemberMapper memberMapper;

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
		    // 각 날짜에 대한 새로운 planScheduleId를 생성
			
		    for (int i=0; i < planRequestDto.getScheduleDates().size(); i++) {
		    	PlanScheduleDto planScheduleDto = planRequestDto.getScheduleDates().get(i);
		        planScheduleDto.setPlanId(planId);
		        planMapper.insertPlanSchedule(planScheduleDto);
		        
				// 일자별 방문 장소 추가
				if (planRequestDto.getPlanLocations().get(i).size() != 0) {
			        for (PlanLocationDto planLocation : planRequestDto.getPlanLocations().get(i)) {
			        	planLocation.setPlanScheduleId(planMapper.searchLastPlanScheduleId());
			            planMapper.insertPlanLocation(planLocation);
			        }
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
	public List<PlanDto> getAllPlanListByMember(String memberId) {
		return planMapper.searchAllPlanList(memberId);
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
		// 여행 기간 일자별 조회
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
		// plan 정보 수정
		PlanDto planDto = planRequestDto.getPlanDto();
		System.out.println(planDto);
		planMapper.updatePlan(planDto);
		// 예약 내역 수정
		List<BookGroupDto> bookGroupDtoList = planRequestDto.getBookContents();
		System.out.println(bookGroupDtoList);
		for (BookGroupDto bookGroupDto : bookGroupDtoList) {
			planMapper.updateBookDetail(bookGroupDto);
		}
		// 여행 기간 일자별 수정
		List<PlanScheduleDto> planScheduleDtoList = planRequestDto.getScheduleDates();
		for (PlanScheduleDto planScheduleDto : planScheduleDtoList) {
			planMapper.updatePlanSchule(planScheduleDto);
		}
		// 결제 내역 수정
		List<PaymentDetailDto> paymentDetailDtoList = planRequestDto.getPaymentDetails();
		for (PaymentDetailDto paymentDetailDto : paymentDetailDtoList) {
			planMapper.updatePaymentDetail(paymentDetailDto);
		}

		// Location, planGroup은 수정 없이 삭제만 가능
	}

	@Override
	public String getMemberIdById(String id) {
		return memberMapper.getMemberIdById(id);
	}

	@Override
	public String getNicknameById(String memberId) {
		return memberMapper.getNicknameByMemberId(memberId);
	}

}
