package com.trip.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trip.member.model.dto.MemberChangePasswordDto;
import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberFileInfoDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.member.model.service.MemberService;
import com.trip.security.TokenDto;

@RestController
@RequestMapping(value = "/api/members", produces = "application/json; charset=utf8")
public class MemberRestController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestPart(name = "signupMember") MemberDto memberDto,
			@RequestPart(name = "image", required = false) MultipartFile file) {
		boolean isRegistered = false;
		if (file != null) {
			isRegistered = memberService.signup(memberDto, file);
		} else {
			isRegistered = memberService.signup(memberDto);
		}
		if (isRegistered) {
			System.out.println("회원가입완료");
			return ResponseEntity.ok("회원가입이 완료되었습니다.");
		} else {
			return ResponseEntity.badRequest().body("회원가입에 실패하였습니다.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
		System.out.println(memberLoginRequestDto);
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
	public ResponseEntity<MemberDto> detailMember(@PathVariable String id) {
		MemberDto memberDto = memberService.findById(id);
		MemberFileInfoDto fileInfo = memberService.fileInfo(memberDto.getMemberId());
		if (fileInfo instanceof MemberFileInfoDto)
			memberDto.setImage(fileInfo.getSaveFile());
		return ResponseEntity.ok(memberDto);

	}

	@PutMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		System.out.println(id);
		boolean isDeleted = memberService.deleteMember(id);
		if (isDeleted) {
			return ResponseEntity.ok(id + " 삭제가 완료되었습니다.");
		} else {
			return ResponseEntity.badRequest().body(id + " 회원 삭제에 실패하였습니다.");
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateMember(@PathVariable String id,
			@RequestPart(name = "updateMember") MemberDto memberDto,
			@RequestPart(name = "image", required = false) MultipartFile file) {
		memberDto.setId(id);
		boolean isUpdated = false;
		if (file != null) {
			isUpdated = memberService.updateMember(memberDto, file);
		} else {
			isUpdated = memberService.updateMember(memberDto);
		}
		if (isUpdated) {
			return ResponseEntity.ok("수정이 완료되었습니다.");
		} else {
			return ResponseEntity.badRequest().body(" 회원 수정에 실패하였습니다.");
		}
	}

	@PutMapping("/changepassword")
	public ResponseEntity<String> changePassword(@RequestBody MemberChangePasswordDto memberChangePassword) {
		boolean isChanged = memberService.changePassword(memberChangePassword);
		if (isChanged) {
			return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
		} else {
			return ResponseEntity.ok("비밀번호 변경에 실패하였습니다.");
		}

	}
}
