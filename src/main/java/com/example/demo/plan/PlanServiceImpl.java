package com.example.demo.plan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.plan.PlanJsonDTO.StudyItem;
import com.example.demo.planDay.PlanDay;
import com.example.demo.planDay.PlanDayRepository;
import com.example.demo.planDay.StatusDay;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	PlanRepository repository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PlanDayRepository planDayRepository;

	@Override
	public int register(PlanDto dto) {

		Plan plan = dtoToEntity(dto);

		repository.save(plan);
		int newNo = plan.getPlanNo();

		return newNo;
	}

	@Override
	public PlanDto read(int planNo) {

		Optional<Plan> optional = repository.findById(planNo);

		if (optional.isPresent()) {
			Plan plan = optional.get();
			PlanDto dto = entityToDto(plan);
			return dto;
		} else {
			return null;
		}
	}

	@Override
	public List<PlanDto> getList(int no) {
		User user = User.builder().userNo(no).build();
		List<Plan> list = repository.findByUser(user);
		List<PlanDto> result = list.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
		return result;
	}

	@Override
	public void modify(PlanDto dto) {
		Optional<Plan> optional = repository.findById(dto.getPlanNo());

		if (optional.isPresent()) {
			Plan plan = optional.get();
			plan.setPlanName(dto.getPlanName());
			plan.setStatus(Status.valueOf(dto.getStatus()));
			repository.save(plan);
		}

	}

	@Override
	public void remove(int planNo) {
		Optional<Plan> optional = repository.findById(planNo);
		if (optional.isPresent()) {
			repository.deleteById(planNo);
		}

	}

	@Override
	public int registerAPI(int userNo, String json) {
		User user = userRepository.findById(userNo).get();
		ObjectMapper mapper = new ObjectMapper();
		PlanJsonDTO dto;
		try {
			System.out.println("안됐음");
			dto = mapper.readValue(json, PlanJsonDTO.class);
			Plan plan = Plan.builder().planName(dto.getStudy()).status(Status.before).user(user).build();
			repository.save(plan);
			for(StudyItem studyItem : dto.getList()) {
				PlanDay planDay = PlanDay.builder().plan(plan)
						.planDayContent(studyItem.getContent())
						.planDayDate(LocalDate.parse(studyItem.getDate()))
						.status(StatusDay.before).build();
				planDayRepository.save(planDay);
			}
		} catch (JsonProcessingException e) {
			System.out.println("안됐음");
			throw new RuntimeException("JSON 파싱 실패", e);
		}
		return 0;
	}

}
