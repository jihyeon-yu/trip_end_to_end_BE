package com.trip.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.trip.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	int insertMember(MemberDto memberDto);
	MemberDto findById(String id);
}
