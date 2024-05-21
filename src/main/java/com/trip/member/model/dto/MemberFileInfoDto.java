package com.trip.member.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberFileInfoDto {
	private String memberFileInfoId;
	private String memberId;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
}
