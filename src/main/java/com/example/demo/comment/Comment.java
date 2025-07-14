package com.example.demo.comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.board.Board;
import com.example.demo.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_comment")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int commentNo;
	
	@ManyToOne
	@JoinColumn(name = "user_no")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "board_no")
	Board board;
	
	@Column(nullable = false)
	String commentContent;
	
	@Column(nullable = false)
	boolean deleteStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentCommentNo")
	private Comment parentComment;
	
	@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Comment> childComments = new ArrayList<>();
	
}
