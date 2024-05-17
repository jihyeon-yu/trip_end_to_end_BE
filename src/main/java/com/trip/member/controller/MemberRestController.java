package com.trip.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.member.model.service.MemberService;
import com.trip.security.TokenDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/api/members", produces = "application/json; charset=utf8")
public class MemberRestController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody MemberDto memberDto) {
		System.out.println(memberDto);
		boolean isRegistered = memberService.signup(memberDto);
		if (isRegistered) {
			System.out.println("회원가입완료");
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
	public ResponseEntity<List<MemberDto>> listMember() {
		List<MemberDto> memberList = memberService.listMember();
		if (memberList.size() != 0) {
			return ResponseEntity.ok(memberList);
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<MemberDto> detailMember(@PathVariable String id){
		MemberDto memberDto = memberService.findById(id);
		return ResponseEntity.ok(memberDto);
		
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody String id){
		boolean isDeleted = memberService.deleteMember(id);
		if(isDeleted) {
			return ResponseEntity.ok(id + " 삭제가 완료되었습니다.");
		}else {
			return ResponseEntity.badRequest().body(id + " 회원 삭제에 실패하였습니다.");
		}
		
	}
	
	@PutMapping("/update/{id}")
	public  ResponseEntity<String> update(@PathVariable String id, @RequestBody MemberDto memberDto) {
		boolean isUpdated = memberService.updateMember(memberDto);
		if(isUpdated) {
			return ResponseEntity.ok(memberDto + " 수정이 완료되었습니다.");
		}else {
			return ResponseEntity.badRequest().body(" 회원 수정에 실패하였습니다.");
		}
	}
}
