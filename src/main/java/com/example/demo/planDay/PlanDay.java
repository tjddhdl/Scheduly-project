package com.example.demo.planDay;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.plan.Plan;
import com.example.demo.plan.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import lombok.Data;
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
	
	@Column(columnDefinition = "json")
	@Convert(converter = PlanDayDetailsConverter.class)
	List<PlanDayDetail> details;
}
