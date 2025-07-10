package com.example.demo.planDay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.user.User;

public interface PlanDayRepository extends JpaRepository<PlanDay, Integer> {
//	List<PlanDay> findByPlanUser(User user);
	
	@Query("SELECT pd FROM PlanDay pd WHERE pd.plan.user.userNo = :userNo")
	List<PlanDay> findByPlanUserNo(@Param("userNo") int userNo);

}
