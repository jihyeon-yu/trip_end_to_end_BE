package com.trip.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.member.model.service.MemberService;
import com.trip.security.TokenDto;

@RestController
@RequestMapping(value = "/api/members", produces = "application/json; charset=utf8")
public class MemberRestController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody MemberDto memberDto) {
		boolean isRegistered = memberService.signup(memberDto);
		if (isRegistered) {
			return ResponseEntity.ok("회원가입이 완료되었습니다.");
		} else {
			return ResponseEntity.badRequest().body("회원가입에 실패하였습니다.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
		TokenDto tokenDto = memberService.login(memberLoginRequestDto);
		if (tokenDto != null) {
			return ResponseEntity.ok(tokenDto);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<MemberDto>> listMember(){
		List<MemberDto> memberList = memberService.listMember();
		if(memberList.size() != 0) {
			return ResponseEntity.ok(memberList);
		}else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
