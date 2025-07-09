package com.example.demo.plan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.user.User;


public interface PlanRepository extends JpaRepository<Plan, Integer> {
	List<Plan> findByUser(User user);
}
