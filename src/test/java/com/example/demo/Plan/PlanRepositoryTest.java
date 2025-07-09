package com.example.demo.Plan;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.plan.Plan;
import com.example.demo.plan.PlanJsonDTO;
import com.example.demo.plan.PlanRepository;
import com.example.demo.plan.Status;
import com.example.demo.plan.PlanJsonDTO.StudyItem;
import com.example.demo.planDay.PlanDay;
import com.example.demo.planDay.PlanDayRepository;
import com.example.demo.planDay.StatusDay;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class PlanRepositoryTest {

	@Autowired
	PlanRepository repository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PlanDayRepository planDayRepository;
	
	@Test
	void 리파지토리() {
		System.out.println("리파지토리: " + repository);
	}

	@Test
	void 플랜추가() {
		User user = new User();
		user.setUserId("1234");
		user.setUserName("짱구");
		user.setPassword("1234");
		user.setRole(Role.free);
		userRepository.save(user);
		Plan plan = Plan.builder().user(user).planName("공부2").status(Status.finished).build();
		repository.save(plan);
		System.out.println(plan);
	}
	
	@Test
	void api플랜추가() {
		User user = userRepository.findById(1).get();
		ObjectMapper mapper = new ObjectMapper();
		PlanJsonDTO dto;
		String json = "{\"study\":\"컴활1급 자격증 공부 계획\",\"list\":["
	            + "{\"date\":\"2022-07-13\",\"content\":\"컴활1급 개요 이해\"},"
	            + "{\"date\":\"2022-07-24\",\"content\":\"모의 시험 및 결과 분석\"}"
	            + "]}";
		try {
			dto = mapper.readValue(json, PlanJsonDTO.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 파싱 실패", e);
		}
		Plan plan = Plan.builder().planName(dto.getStudy()).status(Status.before).user(user).build();
		repository.save(plan);
		for(StudyItem studyItem : dto.getList()) {
			PlanDay planDay = PlanDay.builder().plan(plan)
					.planDayContent(studyItem.getContent())
					.planDayDate(LocalDate.parse(studyItem.getDate()))
					.status(StatusDay.before).build();
			planDayRepository.save(planDay);
		}
	}
}
