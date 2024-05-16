package com.trip.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDto {
	private String memberId;
	private String id;
	private String password;
	private String name;
	private String birthdate;
	private String image;
	private String emailId;
	private String emailDomain;
	private String token;
	private String isActive;
	private String joinDate;
	private String type;
	private String nickname;
	private String sex;
	private String area;
	private String phoneNumber;
	private String mbti;
}
