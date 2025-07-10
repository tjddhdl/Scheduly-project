package com.example.demo.planDay;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanDayDetail {

	private String detail;
	
	@JsonProperty("detailStatus")
	private StatusDay detailStatus;
}
