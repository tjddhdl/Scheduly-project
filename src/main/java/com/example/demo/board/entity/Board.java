package com.example.demo.board.entity;

import java.time.LocalDateTime;

import com.example.demo.apiPlan.entity.APIPlan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_board")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int boardNo;
	
	@OneToOne
	@JoinColumn(name = "api_plan_no")
	APIPlan apiPlan;
	
	@Column(nullable = false, length = 50)
	String boardName;
	
	@Column(nullable = false, length = 1000)
	String board_content;
	
	LocalDateTime boardTime;
}
