package com.example.demo.PlanDay;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.planDay.PlanDayDto;
import com.example.demo.planDay.PlanDayService;

@SpringBootTest
public class PlanDayServiceTest {
	@Autowired
	PlanDayService service;
	
	@Test
	void 생성() {
		PlanDayDto planDayDto = PlanDayDto.builder()
												.planNo(4)
												.planDayDate(LocalDate.now())
												.planDayContent("스프링")
												.status("before")
												.build();
		service.register(planDayDto);
	}
	@Test
	void 조회() {
		PlanDayDto planDayDto = service.read(2);
		
		System.out.println(planDayDto);
	}
	@Test
	void 수정() {
		PlanDayDto planDayDto = service.read(2);
		
		planDayDto.setPlanDayContent("수정테스트");
		service.modify(planDayDto);
	}
	@Test
	void 삭제() {
		
		service.remove(2);
	}
}
