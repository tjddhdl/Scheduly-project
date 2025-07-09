package com.example.demo.PlanDay;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.plan.Plan;
import com.example.demo.plan.Status;
import com.example.demo.planDay.PlanDay;
import com.example.demo.planDay.PlanDayRepository;
import com.example.demo.planDay.StatusDay;


@SpringBootTest
public class PlanDayRepositoryTest {
	@Autowired
	PlanDayRepository repository;
	
	@Test
	void 플랜추가() {
		
		Plan plan = Plan.builder().planNo(1).build();
		
		PlanDay day = PlanDay.builder()
									.plan(plan)
									.planDayDate(LocalDate.of(2025, 7, 9))
									.planDayContent("2단원 공부2")
									.status(StatusDay.ongoing)
									.build();
		
		repository.save(day);
	}
	
}
