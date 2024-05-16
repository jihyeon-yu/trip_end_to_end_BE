package com.trip.member.model.service;

import java.util.List;

import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.security.TokenDto;

public interface MemberService {
	boolean signup(MemberDto memberDto);

	TokenDto login(MemberLoginRequestDto memberLoginRequestDto);

	TokenDto refreshAccessToken(String refreshToken);

	boolean checkIdDuplication(String id);

	List<MemberDto> listMember();

	boolean updateMember(MemberDto memberDto);

	boolean deleteMember(String id);
}
