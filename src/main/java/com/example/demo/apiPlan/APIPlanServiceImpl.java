package com.example.demo.apiPlan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.apiPlan.APIPlan.APIPlanContentList;
import com.example.demo.apiPlan.APIPlan.StudyItem;
import com.example.demo.apiPlan.APIPlan.StudyItemDetail;
import com.example.demo.board.BoardRepository;
import com.example.demo.plan.Plan;
import com.example.demo.plan.PlanRepository;
import com.example.demo.planDay.PlanDay;
import com.example.demo.planDay.PlanDayDetail;
import com.example.demo.planDay.PlanDayRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.transaction.Transactional;

@Service
public class APIPlanServiceImpl implements APIPlanService{

	@Autowired
	APIPlanRepository apiPlanRepository;
	
	@Autowired
	PlanRepository planRepository;
	
	@Autowired
	PlanDayRepository planDayRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public int register(APIPlanDTO dto) throws JsonMappingException, JsonProcessingException {
		APIPlan plan = dtoToEntity(dto);
		apiPlanRepository.save(plan);
		return plan.getApiPlanNo();
	}

	@Override
	public void remove(int no) {
		apiPlanRepository.deleteById(no);
	}

	@Override
	public APIPlanDTO read(int no) throws JsonProcessingException {
		Optional<APIPlan> planOptional = apiPlanRepository.findById(no);
		if(planOptional.isPresent()) {
			APIPlan plan = planOptional.get();
			APIPlanDTO dto = EntitytoDTO(plan);
			return dto;
		}
		return null;
	}

	// apiPlan의 json 반환하는 메소드
	@Override
	public String download(int no) throws JsonProcessingException {
		Optional<APIPlan> optional = apiPlanRepository.findById(no);
		if(optional.isPresent()) {
			APIPlan apiPlan = optional.get();
			APIPlanDTO dto = EntitytoDTO(apiPlan);
			return dto.getApiPlanContentList();
		}
		return null;
	}

	// 플랜 번호를 받아 플랜을 apiplan db에 업로드하는 메서드
	@Override
	public boolean upload(int no, int userNo) {
		
		User user = userRepository.findByUserNo(userNo);
		// plan 옮기기
		APIPlanContentList contentList = new APIPlanContentList();
		Optional<Plan> optional = planRepository.findById(no);
		if(optional.isPresent()) {
			Plan plan = optional.get();
		contentList.setStudy(plan.getPlanName());
		
		// planDay 옮기기
		List<StudyItem> list = new ArrayList<>();
		List<PlanDay> days = planDayRepository.findByPlan(no);
		for(PlanDay day : days) {
			StudyItem studyItem = new StudyItem();
			studyItem.setContent(day.getPlanDayContent());
			studyItem.setDate(day.getPlanDayDate().toString());
			
			// planDayDetails 옮기기
			List<StudyItemDetail> studyItemDetails = new ArrayList<>();
			for(PlanDayDetail detail : day.getDetails()) {
				StudyItemDetail itemDetail = new StudyItemDetail();
				itemDetail.setDetail(detail.getDetail());
				studyItemDetails.add(itemDetail);
			}
			studyItem.setDetails(studyItemDetails);
			list.add(studyItem);
		}
		contentList.setList(list);
		APIPlan apiPlan = new APIPlan();
		apiPlan.setApiPlanContentList(contentList);
		apiPlan.setUser(user);
		apiPlanRepository.save(apiPlan);
		return true;
		}else {
			return false;			
		}
	}

	//유저번호로 api플랜목록 반환
	@Override
	@Transactional
	public List<APIPlanDTO> readByUserId(String userId) {
		User user = userRepository.findByUserId(userId);
		List<APIPlan> list = apiPlanRepository.findByUser(user);
		for(int i =list.size()-1; i>=0; i--) {
			if(boardRepository.findByApiPlan(list.get(i))!=null) {
				list.remove(i);
			}
		}
		List<APIPlanDTO> dtoList = list.stream().map(entity->{
			try {
				return EntitytoDTO(entity);
			} catch (JsonProcessingException e) {
				return null;
			}
		}).collect(Collectors.toList());
		return dtoList;
	}

}
