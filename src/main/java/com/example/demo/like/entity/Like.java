package com.example.demo.like.entity;

import java.time.LocalDate;

import com.example.demo.plan.entity.Plan;
import com.example.demo.plan.entity.Status;
import com.example.demo.planDay.entity.PlanDay;
import com.example.demo.user.entity.User;

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
	
//	@ManyToOne
//	@JoinColumn(name = "board")
//	Boadrd board;
	
	@ManyToOne
	@JoinColumn(name = "plan")
	Plan plan;
}
