package com.trip.plan.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class PlanServiceImpl implements PlanService{
	
	@Autowired
	private PlanMapper planMapper;
	
	@Override
	public void createPlan(PlanRequestDto planRequestDto) {
		PlanDto planDto = new PlanDto();
		planDto.setTitle(planRequestDto.getTitle());
		planDto.setStartDate(planRequestDto.getStartDate());
		planDto.setEndDate(planRequestDto.getEndDate());
		planMapper.insertPlan(planDto);
		String planId = planMapper.searchLastPlanId();
		// 참여 맴버 추가
		if(planRequestDto.getMemberIds().length != 0) {
			for(String memberId : planRequestDto.getMemberIds()) {
				PlanGroupDto planGroupDto = new PlanGroupDto();
				planGroupDto.setPlanId(planId);
				planGroupDto.setMemberId(memberId);
				planMapper.insertPlanMember(planGroupDto);
			}
		}
		if(planRequestDto.getScheduleDates().length != 0) {
			for(String date : planRequestDto.getScheduleDates()) {
				PlanScheduleDto planScheduleDto = new PlanScheduleDto();
				planScheduleDto.setDate(date);
				planScheduleDto.setPlanId(planId);
				planMapper.insertPlanSchedule(planScheduleDto);
			}
		}
		if(planRequestDto.getPlanLocations().size() != 0) {
			String planScheduleId = planMapper.searchLastPlanScheduleId();
			for(PlanLocationDto planLocationDto : planRequestDto.getPlanLocations()) {
				planLocationDto.setPlanScheduleId(planScheduleId);
				planMapper.insertPlanLocation(planLocationDto);
			}
		}
		if(planRequestDto.getBookContents().length != 0) {
			for(String content : planRequestDto.getBookContents()) {
				BookGroupDto bookGroupDto = new BookGroupDto();
				bookGroupDto.setPlanId(planId);
				bookGroupDto.setContent(content);
				planMapper.insertBookDetail(bookGroupDto);
			}
		}
		if(planRequestDto.getPaymentDetails().size() != 0) {
			for(PaymentDetailDto paymentDetailDto : planRequestDto.getPaymentDetails()) {
				paymentDetailDto.setPlanId(planId);
				planMapper.insertPaymentDetail(paymentDetailDto);
			}
		}
	}

	@Override
	public PlanDto getPlan(String planId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePlan(PlanDto planDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePlan(String planId) {
		// TODO Auto-generated method stub
		
	}

}
