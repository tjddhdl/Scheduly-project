package com.example.demo.board;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	int boardNo;
	
	int apiPlan;
	
	String boardName;
	
	String userName;
	
	String boardContent;
	
	int likeCount;
	
	LocalDateTime boardTime;
}
