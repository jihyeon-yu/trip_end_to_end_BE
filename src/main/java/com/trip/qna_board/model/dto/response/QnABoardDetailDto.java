package com.trip.qna_board.model.dto.response;

import com.trip.qna_board.model.dto.QnABoardDto;
import com.trip.qna_board.model.dto.QnACommentDto;

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
public class QnABoardDetailDto {
	private QnABoardDto qnaBoardDto;
	private QnACommentDto qnaCommentDto;
}
