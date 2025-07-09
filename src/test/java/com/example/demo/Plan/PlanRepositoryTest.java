package com.example.demo.Plan;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.plan.Plan;
import com.example.demo.plan.PlanRepository;
import com.example.demo.plan.Status;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@SpringBootTest
public class PlanRepositoryTest {

	@Autowired
	PlanRepository repository;
	@Autowired
	UserRepository userRepository;

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
		Plan plan = Plan.builder().user(user).planName("공부3").status(Status.finished).build();
		repository.save(plan);
		System.out.println(plan);
	}
}
