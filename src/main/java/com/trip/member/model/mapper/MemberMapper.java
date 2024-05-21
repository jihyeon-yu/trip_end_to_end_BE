package com.trip.member.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberFileInfoDto;

@Mapper
public interface MemberMapper {
	int insertMember(MemberDto memberDto);
	MemberDto findById(String id);
	List<MemberDto> listMember();
	void updateMember(MemberDto memberDto);
	void changePassword(MemberDto memberDto);
	void updateToken(@Param("id") String id, @Param("token") String token);
	void deleteMember(@Param("id") String id);
	String getMemberIdById(String id);
	
	void registerFile(MemberFileInfoDto fileInfo);
	MemberFileInfoDto fileInfo(String memberId);
}
