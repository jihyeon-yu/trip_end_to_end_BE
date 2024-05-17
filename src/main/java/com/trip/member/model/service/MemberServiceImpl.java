package com.trip.member.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public TokenDto login(MemberLoginRequestDto memberLoginRequestDto) {
        MemberDto memberDto = memberMapper.findById(memberLoginRequestDto.getId());
        if (memberDto != null && passwordEncoder.matches(memberLoginRequestDto.getPassword(), memberDto.getPassword())) {
            String accessToken = jwtUtil.generateAccessToken(memberDto.getId());
            String refreshToken = jwtUtil.generateRefreshToken(memberDto.getId());
            // refreshToken을 데이터베이스에 저장
            memberMapper.updateToken(memberDto.getId(), refreshToken);
            return new TokenDto(accessToken, refreshToken);
        }
        return null;
    }

    @Override
    public TokenDto refreshAccessToken(String refreshToken) {
        if (jwtUtil.isTokenExpired(refreshToken)) {
            return null; // 리프레시 토큰이 만료된 경우
        }
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(username);
        return new TokenDto(newAccessToken, refreshToken);
    }

    @Override
    public boolean signup(MemberDto memberDto) {
        if (memberMapper.findById(memberDto.getId()) != null) {
            return false; // 아이디가 이미 존재하는 경우
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberMapper.insertMember(memberDto);
        return true;
    }

    @Override
    public boolean checkIdDuplication(String id) {
        return memberMapper.findById(id) != null;
    }

    @Override
    public List<MemberDto> listMember() {
        return memberMapper.listMember();
    }

    @Override
    public boolean updateMember(MemberDto memberDto) {
        if (memberMapper.findById(memberDto.getId()) == null) {
            return false; // 해당 아이디가 존재하지 않는 경우
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberMapper.updateMember(memberDto);
        return true;
    }

    @Override
    public boolean deleteMember(String id) {
    	MemberDto memberDto = memberMapper.findById(id);
        if (memberDto == null) {
            return false; // 해당 아이디가 존재하지 않는 경우
        }
        memberMapper.deleteMember(id);
        return true;
    }

	@Override
	public MemberDto findById(String memberId) {
		return memberMapper.findById(memberId);
	}
}
