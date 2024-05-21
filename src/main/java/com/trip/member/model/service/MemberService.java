package com.trip.member.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.trip.member.model.dto.MemberChangePasswordDto;
import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberFileInfoDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.security.TokenDto;

public interface MemberService {
	boolean signup(MemberDto memberDto, MultipartFile file);
	
	boolean signup(MemberDto memberDto);

	TokenDto login(MemberLoginRequestDto memberLoginRequestDto);

	TokenDto refreshAccessToken(String refreshToken);

	boolean checkIdDuplication(String id);

	List<MemberDto> listMember();
	
	MemberDto findById(String memberId);

	boolean updateMember(MemberDto memberDto);
	
	boolean changePassword(MemberChangePasswordDto memberChangePasswordDto);

	boolean deleteMember(String id);
	
	MemberFileInfoDto fileInfo(String memberId);
}
