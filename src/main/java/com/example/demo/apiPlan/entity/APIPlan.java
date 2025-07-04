package com.example.demo.apiPlan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_api_plan")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class APIPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int apiPlanNo;
	
	@Column(nullable = false, length = 50)
	String apiPlanName;
}
