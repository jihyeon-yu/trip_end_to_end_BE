package com.trip.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.member.model.mapper.MemberMapper;
import com.trip.security.JwtUtil;
import com.trip.security.TokenDto;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private JwtUtil jwtUtil;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public boolean register(MemberDto memberDto) {
		// 비밀번호 암호화
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
		// 회원 등록 로직 구현
		return memberMapper.insertMember(memberDto) > 0;
	}

	@Override
	public TokenDto login(MemberLoginRequestDto memberLoginRequestDto) {
		MemberDto memberDto = memberMapper.findById(memberLoginRequestDto.getId());
		if (memberDto != null
				&& passwordEncoder.matches(memberLoginRequestDto.getPassword(), memberDto.getPassword())) {
			String token = jwtUtil.generateToken(memberDto.getId());
			return new TokenDto(token);
		}
		return null;
	}

}
