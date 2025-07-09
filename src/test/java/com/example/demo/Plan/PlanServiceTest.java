package com.example.demo.Plan;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.plan.PlanDto;
import com.example.demo.plan.PlanService;

@SpringBootTest
public class PlanServiceTest {
	@Autowired
	PlanService service;
	@Test
	void 플랜생성() {
		PlanDto dto = PlanDto.builder().userNo(2).planName("생성").status("before").build();
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
	void 삭제() {
		service.remove(4);
	}
	
	void json추가() {
		String json = "{\"study\":\"컴활1급 자격증 공부 계획\",\"list\":[{\"date\":\"2022-07-13\",\"content\":\"컴활1급 개요 이해\"},{\"date\":\"2022-07-24\",\"content\":\"모의 시험 및 결과 분석\"}]}";
		service.registerAPI(1, json);
	}
}
