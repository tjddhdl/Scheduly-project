package com.example.demo.APIPlan;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.apiPlan.APIPlan;
import com.example.demo.apiPlan.APIPlanRepository;
import com.example.demo.apiPlan.APIPlan.APIPlanContentList;
import com.example.demo.apiPlan.APIPlan.StudyItem;
import com.example.demo.apiPlan.APIPlan.StudyItemDetail;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class APIPlanRepositoryTest {

	@Autowired
	APIPlanRepository apiPlanRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	void 플랜등록() {
		List<StudyItemDetail> black = new ArrayList<>();
		List<StudyItemDetail> lost = new ArrayList<>();
		StudyItemDetail detail = new StudyItemDetail("1");
		StudyItemDetail detail2 = new StudyItemDetail("2");
		StudyItemDetail detail3 = new StudyItemDetail("3");
		lost.add(detail);
		lost.add(detail2);
		lost.add(detail3);
		APIPlanContentList list = APIPlanContentList.builder()
				.study("컴퓨터 일반").list(List.of(
						new StudyItem("2025-07-04", "컴퓨터구조(하드웨어, CPU)", lost),
						new StudyItem("2025-07-05", "기억장치", black),
						new StudyItem("2025-07-06", "입출력장치", black)
						)).build();
		APIPlan testPlan = new APIPlan().builder()
				.apiPlanContentList(list)
				.build();
		User user = User.builder().userNo(3).build();
		testPlan.setUser(user);
		apiPlanRepository.save(testPlan);
	}
	
	@Test
	void 플랜삭제() {
		apiPlanRepository.deleteById(5);
	}
	
	@Test
	void 플랜조회() {
		APIPlan apiPlan = apiPlanRepository.findById(4).get();
		System.out.println(apiPlan);
	}
	
	@Test
	void 플랜수정() {
		List<StudyItemDetail> details = new ArrayList<>();
		APIPlan.APIPlanContentList list = APIPlanContentList.builder()
				.study("테스트테스트").list(List.of(
						new APIPlan.StudyItem("2025-07-04", "테스트", details),
						new APIPlan.StudyItem("2025-07-05", "테스트", details),
						new APIPlan.StudyItem("2025-07-06", "테스트", details)
						)).build();
		APIPlan apiPlan = apiPlanRepository.findById(2).get();
		apiPlan.setApiPlanContentList(list);
		apiPlanRepository.save(apiPlan);
	}
	
	@Transactional
	@Test
	void 플랜목록조회() {
		User user = userRepository.findByUserId("dddd");
		System.out.println(user);
		List<APIPlan> list = apiPlanRepository.findByUser(user);
		System.out.println(list);
	}
}
