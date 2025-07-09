package com.example.demo.planDay;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanDayServiceImpl implements PlanDayService {

	@Autowired
	PlanDayRepository repository;

	@Override
	public int register(PlanDayDto dayDto) {

		PlanDay planDay = dtoToEntity(dayDto);

		repository.save(planDay);
		int newNo = planDay.getPlanDayNo();

		return newNo;
	}

	@Override
	public PlanDayDto read(int planDayNo) {

		Optional<PlanDay> optional = repository.findById(planDayNo);
		if (optional.isPresent()) {
			PlanDay planDay = optional.get();
			PlanDayDto planDayDto = entityToDto(planDay);
			return planDayDto;
		} else {

			return null;
		}
	}

	@Override
	public void modify(PlanDayDto planDayDto) {

		Optional<PlanDay> optional = repository.findById(planDayDto.getPlanDayNo());

		if (optional.isPresent()) {
			PlanDay planDay = optional.get();

			planDay.setPlanDayDate(planDayDto.getPlanDayDate());
			planDay.setPlanDayContent(planDayDto.getPlanDayContent());
			planDay.setStatus(StatusDay.valueOf(planDayDto.getStatus()));

			repository.save(planDay);
		}

	}

	@Override
	public void remove(int planDayNo) {

		Optional<PlanDay> optional = repository.findById(planDayNo);

		if (optional.isPresent()) {
			repository.deleteById(planDayNo);
		}
	}

}
