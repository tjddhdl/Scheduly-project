package com.example.demo.planDay;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.plan.Plan;

public interface PlanDayService {
	int register(PlanDayDto dayDto);

	PlanDayDto read(int planDayNo);
	
	List<PlanDayDto> getList(int no);
	

	List<PlanDayDto> getListByPlanNoToAPI(int userNo, int planNo);

	List<PlanDayDto> getListByPlanNo(int planNo);

	void modify(PlanDayDto planDayDto);

	PlanDayDetail jsonModify(int planDayNo, int detailKey, String detail);
	
	PlanDayDto dayDetailStatusCheck(int planDayNo);
	
	void remove(int planDayNo);
	
	void statusChange(int planDayNo, int detailKey);
	

	PlanDayDetail addJson(int planDayNo, String content);

	
	void removeJson(int planDayNo, int detailKey);
	
	void moveJson(int planDayNo, int detailKey, String move);
	
	void movePlanDayDate(int planDayNo, LocalDate newDate);
	

	List<PlanDayDto> reArray(List<PlanDayDto> list, LocalDate date);
	
	List<PlanDayDto> pushDateToList(int planNo, int planDayNo);
	
	List<PlanDayDto> pullDateToList(int planNo, int planDayNo);
	

	public boolean isPlanDayEmpty(int planDayNo);
	
	void toggleAllStatus(int planDayNo);
	void removePlanDay(int planDayNo);

	default PlanDayDto entityToDto(PlanDay planDay) {
		int plan = planDay.getPlan().getPlanNo();
		PlanDayDto plandayDto = PlanDayDto.builder()
										.planDayNo(planDay.getPlanDayNo())
										.planNo(plan)
										.planDayDate(planDay.getPlanDayDate())
										.planDayContent(planDay.getPlanDayContent())
										.status(planDay.getStatus().toString())
										.details(planDay.getDetails())
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
									.details(dayDto.getDetails().stream().map(d -> new PlanDayDetail(d.getDetail(), d.getDetailStatus()))
											.collect(Collectors.toList()))
									.build();

		return planDay;
	}
}
