package com.trip.member.model.service;

import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.security.TokenDto;

public interface MemberService {
	boolean register(MemberDto memberDto);
	TokenDto login(MemberLoginRequestDto memberLoginRequestDto);
}
