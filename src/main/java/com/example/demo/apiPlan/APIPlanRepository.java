package com.example.demo.apiPlan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.user.User;


public interface APIPlanRepository extends JpaRepository<APIPlan, Integer> {

	List<APIPlan> findByUser(User user);
}
