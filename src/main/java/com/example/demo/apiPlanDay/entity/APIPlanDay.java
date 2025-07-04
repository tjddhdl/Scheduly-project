package com.example.demo.apiPlanDay.entity;

import com.example.demo.apiPlan.entity.APIPlan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_api_plan_day")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class APIPlanDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int apiPlanDayNo;
	
	@OneToMany
	@JoinColumn(name = "api_plan_no")
	APIPlan apiPlan;
	
	@Column(nullable = false, length = 50)
	String apiPlanDayContent;
}
