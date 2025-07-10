package com.example.demo.PlanDay;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.plan.Plan;
import com.example.demo.planDay.PlanDay;
import com.example.demo.planDay.PlanDayDetail;
import com.example.demo.planDay.PlanDayRepository;
import com.example.demo.planDay.StatusDay;


@SpringBootTest
public class PlanDayRepositoryTest {
	@Autowired
	PlanDayRepository repository;
	
	@Test
	void 플랜추가() {
		
		Plan plan = Plan.builder().planNo(1).build();
		List<PlanDayDetail> list = new ArrayList<>();
		PlanDayDetail detail = new PlanDayDetail("1페이지", StatusDay.BEFORE);
		list.add(detail);
		PlanDay day = PlanDay.builder()
									.plan(plan)
									.planDayDate(LocalDate.of(2025, 7, 9))
									.planDayContent("2단원 공부2")
									.status(StatusDay.BEFORE)
									.details(list)
									.build();
		
		repository.save(day);
	}
	
	
}
