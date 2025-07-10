package com.example.demo.planDay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Service
public class PlanDayServiceImpl implements PlanDayService {

	@Autowired
	PlanDayRepository repository;
	
	@Autowired
	UserRepository userRepository;

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
			PlanDay apply = dtoToEntity(planDayDto);
			planDay.setPlanDayDate(planDayDto.getPlanDayDate());
			planDay.setPlanDayContent(planDayDto.getPlanDayContent());
			planDay.setStatus(StatusDay.valueOf(planDayDto.getStatus()));
			planDay.setDetails(apply.getDetails());
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

	@Override
	public List<PlanDayDto> getList(int userNo) {
		
		List<PlanDay> list = repository.findByPlanUserNo(userNo);

		List<PlanDayDto> result = list.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
		return result;
	}

}
