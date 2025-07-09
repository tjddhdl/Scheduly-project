package com.example.demo.planDay;


import java.util.List;

import com.example.demo.plan.Plan;

public interface PlanDayService {
	int register(PlanDayDto dayDto);

	PlanDayDto read(int planDayNo);
	
	List<PlanDayDto> getList(int no);

	void modify(PlanDayDto planDayDto);

	void remove(int planDayNo);

	default PlanDayDto entityToDto(PlanDay planDay) {
		int plan = planDay.getPlan().getPlanNo();
		PlanDayDto plandayDto = PlanDayDto.builder()
										.planDayNo(planDay.getPlanDayNo())
										.planNo(plan)
										.planDayDate(planDay.getPlanDayDate())
										.planDayContent(planDay.getPlanDayContent())
										.status(planDay.getStatus().toString())
										.build();
		return plandayDto;
	}

	default PlanDay dtoToEntity(PlanDayDto dayDto) {
		Plan plan = Plan.builder().planNo(dayDto.getPlanNo()).build();

		PlanDay planDay = PlanDay.builder()
									.planDayNo(dayDto.getPlanDayNo())
									.plan(plan)
									.planDayDate(dayDto.getPlanDayDate())
									.planDayContent(dayDto.getPlanDayContent())
									.status(StatusDay.valueOf(dayDto.getStatus()))
									.build();

		return planDay;
	}
}
