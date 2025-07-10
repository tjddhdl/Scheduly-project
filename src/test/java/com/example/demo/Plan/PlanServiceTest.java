package com.example.demo.Plan;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.gpt.GptService;
import com.example.demo.plan.PlanDto;
import com.example.demo.plan.PlanService;

@SpringBootTest
public class PlanServiceTest {
	@Autowired
	PlanService service;
	
	@Autowired
	GptService gptService;
	
	@Test
	void 플랜생성() {
		PlanDto dto = PlanDto.builder().userNo(3).planName("테스트중").status("before").build();
		service.register(dto);
	}
	@Test
	void 조회() {
		PlanDto dto = service.read(3);
		
		System.out.println(dto);
	}
	@Test
	void userNo로조회() {
		List<PlanDto> dto = service.getList(3);
		System.out.println(dto);
	}
	@Test
	void 수정() {
		PlanDto dto = service.read(4);
		dto.setPlanName("수정");
		dto.setStatus("before");
		service.modify(dto);
	}
	@Test
	void 삭제() {
		service.remove(4);
	}
	
	@Test
	void json추가() {
		String json = gptService.createPlan("2025-07-09부터 간단한 요리 연습 플랜 10일치 짜줘");
		System.out.println(json);
		service.registerAPI(1, json);
	}
}
