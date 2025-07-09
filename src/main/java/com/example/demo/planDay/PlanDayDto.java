package com.example.demo.planDay;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDayDto {
	int planDayNo;
	int planNo;
	LocalDate planDayDate;
	String planDayContent;
	String status;
}
