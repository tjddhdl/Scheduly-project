package com.example.demo.plan;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.planDay.PlanDayDto;
import com.example.demo.user.User;

public interface PlanService {
	// 테스트
	int register(PlanDto dto);

	PlanDto read(int planNo);

	// userNo로 조회
	List<PlanDto> getList(int no);

	void modify(PlanDto dto);

	void remove(int planNo);
	
	int registerAPI(int userNo, String json);
	
	boolean updateStatusIfAllTasksFinished(int planNo);

	default PlanDto entityToDto(Plan plan) {
		int user = plan.getUser().getUserNo();
		PlanDto dto = PlanDto.builder().planNo(plan.getPlanNo()).userNo(user).planName(plan.getPlanName())
				.status(plan.getStatus().toString()).build();
		return dto;
	}

	default Plan dtoToEntity(PlanDto dto) {
		User user = User.builder().userNo(dto.getUserNo()).build();

		Plan plan = Plan.builder().planNo(dto.getPlanNo()).user(user).planName(dto.getPlanName())
				.status(Status.valueOf(dto.getStatus())).build();

		return plan;
	}
}
