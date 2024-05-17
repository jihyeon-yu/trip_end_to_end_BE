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
public class MemberChangePasswordDto {
	private String id;
	private String currentPassword;
	private String newPassword;
	private String newPasswordCheck;
}
