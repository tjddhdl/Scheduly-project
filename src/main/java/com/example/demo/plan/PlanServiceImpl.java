package com.example.demo.plan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.plan.PlanJsonDTO.StudyItem;
import com.example.demo.plan.PlanJsonDTO.StudyItem.StudyItemDetail;
import com.example.demo.planDay.PlanDay;
import com.example.demo.planDay.PlanDayDetail;
import com.example.demo.planDay.PlanDayRepository;
import com.example.demo.planDay.StatusDay;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

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
			if (dto.getStatus() != null) {
			plan.setStatus(Status.valueOf(dto.getStatus()));
			}
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
			dto = mapper.readValue(json, PlanJsonDTO.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 파싱 실패", e);
		}
		Plan plan = Plan.builder().planName(dto.getStudy()).status(Status.BEFORE).user(user).build();
		repository.save(plan);
		for (StudyItem studyItem : dto.getList()) {
			PlanDay planDay = PlanDay.builder().plan(plan).planDayContent(studyItem.getContent())
					.planDayDate(LocalDate.parse(studyItem.getDate())).status(StatusDay.BEFORE).build();
			if (studyItem.getDetails() != null && !studyItem.getDetails().isEmpty()) {
				List<PlanDayDetail> detailes = new ArrayList<>();
				for (StudyItemDetail detailDto : studyItem.getDetails()) {
					PlanDayDetail detail = PlanDayDetail.builder().detail(detailDto.getDetail())
							.detailStatus(StatusDay.BEFORE).build();
					detailes.add(detail);
				}
				planDay.setDetails(detailes);
			}
			planDayRepository.save(planDay);
		}
		return plan.planNo;
	}

	@Override
	public boolean updateStatusIfAllTasksFinished(int planNo) {
		
		List<PlanDay> planDays = planDayRepository.findByPlan_PlanNo(planNo);

        // 하나라도 완료 안된 task 있으면 false 반환
        boolean allFinished = planDays.stream()
            .flatMap(planDay -> planDay.getDetails().stream())
            .allMatch(detail -> detail.getDetailStatus() == StatusDay.FINISHED);

        if (!allFinished) {
            return false;
        }

        // 모두 완료됐으면 플랜 상태 변경
        Plan plan = repository.findById(planNo)
            .orElseThrow(() -> new EntityNotFoundException("플랜을 찾을 수 없습니다"));

        plan.setStatus(Status.FINISHED);
        repository.save(plan);

        return true;
	}

}
