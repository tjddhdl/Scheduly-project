package com.example.demo.planDay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.user.User;

public interface PlanDayRepository extends JpaRepository<PlanDay, Integer> {
//	List<PlanDay> findByPlanUser(User user);
	
	@Query("SELECT pd FROM PlanDay pd WHERE pd.plan.user.userNo = :userNo")
	List<PlanDay> findByPlanUserNo(@Param("userNo") int userNo);

	@Query("SELECT pd FROM PlanDay pd WHERE pd.plan.planNo = :plan")
	List<PlanDay> findByPlan(@Param("plan")int plan);
	
	List<PlanDay> findByPlan_User_UserNoAndPlan_PlanNo(int userNo, int planNo);
	
	List<PlanDay> findByPlan_PlanNo(int planNo);
	
	@Modifying
	@Query("DELETE FROM PlanDay pd WHERE pd.plan.planNo = :planNo")
	void deleteByPlanNo(@Param("planNo") int planNo);

}
