package com.example.demo.PlanDay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.planDay.PlanDayDetail;
import com.example.demo.planDay.PlanDayDto;
import com.example.demo.planDay.PlanDayService;

@SpringBootTest
public class PlanDayServiceTest {
	@Autowired
	PlanDayService service;
	
	@Test
	void 생성() {
		List<PlanDayDetail> list = new ArrayList<>();
		PlanDayDto planDayDto = PlanDayDto.builder()
												.planNo(3)
												.planDayDate(LocalDate.of(2025, 7, 15))
												.planDayContent("테스트중")
												.status("BEFORE")
												.details(list)
												.planDayDate(LocalDate.of(2025, 7, 10))
												.planDayContent("테스트중ㅇㅇ")
												.status("BEFORE")
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
	
	@Test
	void json수정() {
		String detail = "웅나";
		service.jsonModify(1, 0, detail);
	}
	@Test
	void jsonstatus() {
		service.statusChange(1, 0);
	}
	@Test
	void addJson() {
		service.addJson(36, "뜌땨");
	}
	@Test
	void removeJson() {
		service.removeJson(36, 0);
	}
	
	@Test
	void moveJson() {
		service.moveJson(1, 2, "up");
	}
	
	@Test
	void 날짜정렬() {
		LocalDate date = LocalDate.now();
		List<PlanDayDto> list = service.getListByPlanNo(12);
		service.reArray(list, date);
	}
	
	@Test
	void 날짜미루기() {
		service.pushDateToList(12, 40);
	}
	
	@Test
	void 디테일status검사() {
		service.dayDetailStatusCheck(42);
	}

}
