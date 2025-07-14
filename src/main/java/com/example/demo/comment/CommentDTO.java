package com.example.demo.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class CommentDTO {
	
	int commentNo;
	
	int user;
	
	int board;
	
	String commentContent;
	
	LocalDateTime commentTime;
	
	Integer parentCommentNo;
	
	private boolean deleteStatus;
	
	private List<CommentDTO> childComments;
}
