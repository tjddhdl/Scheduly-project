package com.example.demo.planDay;

import java.util.ArrayList;
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

	// detail 정보 수정할때
	@Override
	public void jsonModify(int planDayNo, int detailKey, String detail) {
		PlanDay day = repository.findById(planDayNo).get();
		List<PlanDayDetail> list = day.getDetails();
		if(list.size()>detailKey) {
		PlanDayDetail dayDetail = list.get(detailKey);
		dayDetail.setDetail(detail);
		repository.save(day);
		}
	}

	@Override
	public void statusChange(int planDayNo, int detailKey) {

		PlanDay day = repository.findById(planDayNo).get();
		List<PlanDayDetail> list = day.getDetails();
		if(list.size()>detailKey) {
			PlanDayDetail dayDetail = list.get(detailKey);
			if(dayDetail.getDetailStatus()==StatusDay.BEFORE) {
				dayDetail.setDetailStatus(StatusDay.FINISHED);
			}else {
				dayDetail.setDetailStatus(StatusDay.BEFORE);
			}
		}
		repository.save(day);
	}

//	@Override
//	public void addJson(int planDayNo, String content) {
//		PlanDay day = repository.findById(planDayNo).get();
//		List<PlanDayDetail> list = day.getDetails();
//		PlanDayDetail detail = PlanDayDetail.builder().detail(content).detailStatus(StatusDay.BEFORE).build();
//		list.add(detail);
//		repository.save(day);
//	}
	@Override
	public PlanDayDetail addJson(int planDayNo, String content) {
	    PlanDay day = repository.findById(planDayNo)
	            .orElseThrow(() -> new RuntimeException("PlanDay not found"));

	    List<PlanDayDetail> list = day.getDetails();

	    PlanDayDetail detail = PlanDayDetail.builder()
	            .detail(content)
	            .detailStatus(StatusDay.BEFORE)
	            .build();

	    list.add(detail);

	    repository.save(day);

	    
	    return detail;
	}


	@Override
	public void removeJson(int planDayNo, int detailKey) {
		PlanDay day = repository.findById(planDayNo).get();
		List<PlanDayDetail> list = day.getDetails();
		list.remove(detailKey);
		repository.save(day);
	}

	@Override
	public void moveJson(int planDayNo, int detailKey, String move) {
		PlanDay day = repository.findById(planDayNo).get();
		List<PlanDayDetail> list = day.getDetails();
		if(move.matches("up")) {
			if(detailKey>0&&detailKey<list.size()) {
				PlanDayDetail detail = list.get(detailKey-1);
				list.set(detailKey-1, list.get(detailKey));
				list.set(detailKey, detail);
			}
		}else if(move.matches("down")) {
			if(detailKey<list.size()-1&&detailKey>=0) {
				PlanDayDetail detail = list.get(detailKey+1);
				list.set(detailKey+1, list.get(detailKey));
				list.set(detailKey, detail);
			}
		}
		repository.save(day);
	}
}
