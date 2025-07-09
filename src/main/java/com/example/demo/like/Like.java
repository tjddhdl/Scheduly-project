package com.example.demo.like;

import java.time.LocalDate;

import com.example.demo.board.Board;
import com.example.demo.plan.Plan;
import com.example.demo.plan.Status;
import com.example.demo.planDay.PlanDay;
import com.example.demo.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_like")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int likeNo;
	
	@ManyToOne
	@JoinColumn(name = "user")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "board")
	Board board;
}
