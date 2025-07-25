package com.example.demo.apiPlan;

import java.util.List;

import com.example.demo.apiPlan.APIPlan.APIPlanContentList;
import com.example.demo.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface APIPlanService {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	default APIPlan dtoToEntity(APIPlanDTO dto) throws JsonMappingException, JsonProcessingException {
		APIPlanContentList contentList = objectMapper.readValue(dto.getApiPlanContentList(), APIPlanContentList.class);
		User user = User.builder().userNo(dto.getUser()).build();
		APIPlan apiPlan = APIPlan.builder().apiPlanNo(dto.getApiPlanNo()).user(user)
				.apiPlanContentList(contentList).build();
		return apiPlan;
	}
	
	default APIPlanDTO EntitytoDTO(APIPlan apiPlan) throws JsonProcessingException {
		String json = objectMapper.writeValueAsString(apiPlan.getApiPlanContentList());
		APIPlanDTO dto = APIPlanDTO.builder().apiPlanNo(apiPlan.getApiPlanNo()).user(apiPlan.getUser().getUserNo())
				.apiPlanContentList(json).build();
		return dto;
	}
	
	int register(APIPlanDTO dto) throws JsonMappingException, JsonProcessingException;
	void remove(int no);
	APIPlanDTO read (int no) throws JsonProcessingException;
	String download(int no) throws JsonProcessingException;
	
	List<APIPlanDTO> readByUserId(String userId);
	
	// 플랜 번호를 받아 플랜을 apiplan db에 업로드하는 메서드
	boolean upload(int no, int userNo);
}
