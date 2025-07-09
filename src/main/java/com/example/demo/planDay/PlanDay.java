package com.example.demo.planDay;

import java.time.LocalDate;

import com.example.demo.plan.Plan;
import com.example.demo.plan.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tbl_plan_day")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PlanDay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int planDayNo;
	
	@ManyToOne
	@JoinColumn(name = "plan")
	Plan plan;
	
	@Column
	LocalDate planDayDate;
	
	@Column
	String planDayContent;
	
	@Enumerated(EnumType.STRING)
	@Column
	StatusDay status;
	
}
