package com.example.demo.APIPlan;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.apiPlan.APIPlan;
import com.example.demo.apiPlan.APIPlanRepository;
import com.example.demo.apiPlan.APIPlan.APIPlanContentList;

@SpringBootTest
public class APIPlanRepositoryTest {

	@Autowired
	APIPlanRepository apiPlanRepository;
	
	@Test
	void 플랜등록() {
		APIPlan.APIPlanContentList list = APIPlanContentList.builder()
				.study("컴퓨터 일반").list(List.of(
						new APIPlan.StudyItem("2025-07-04", "컴퓨터구조(하드웨어, CPU)"),
						new APIPlan.StudyItem("2025-07-05", "기억장치"),
						new APIPlan.StudyItem("2025-07-06", "입출력장치")
						)).build();
		APIPlan testPlan = new APIPlan().builder()
				.apiPlanContentList(list)
				.build();
		apiPlanRepository.save(testPlan);
	}
	
	@Test
	void 플랜삭제() {
		apiPlanRepository.deleteById(5);
	}
	
	@Test
	void 플랜조회() {
		APIPlan apiPlan = apiPlanRepository.findById(2).get();
		System.out.println(apiPlan);
	}
	
	@Test
	void 플랜수정() {
		APIPlan.APIPlanContentList list = APIPlanContentList.builder()
				.study("테스트테스트").list(List.of(
						new APIPlan.StudyItem("2025-07-04", "테스트"),
						new APIPlan.StudyItem("2025-07-05", "테스트"),
						new APIPlan.StudyItem("2025-07-06", "테스트")
						)).build();
		APIPlan apiPlan = apiPlanRepository.findById(2).get();
		apiPlan.setApiPlanContentList(list);
		apiPlanRepository.save(apiPlan);
	}
}
