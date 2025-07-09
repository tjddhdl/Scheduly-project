package com.example.demo.planDay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.user.User;

public interface PlanDayRepository extends JpaRepository<PlanDay, Integer> {
	List<PlanDay> findByPlanUser(User user);
}
