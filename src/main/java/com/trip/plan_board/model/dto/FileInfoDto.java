package com.trip.plan_board.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FileInfoDto {
	private String fileInfoId;
	private String planBoardId;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
	private String type;
}
