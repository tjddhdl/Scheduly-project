package com.example.demo.apiPlan;

import com.example.demo.apiPlan.APIPlan.APIPlanContentList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface APIPlanService {
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	default APIPlan dtoToEntity(APIPlanDTO dto) throws JsonMappingException, JsonProcessingException {
		APIPlanContentList contentList = objectMapper.readValue(dto.getApiPlanContentList(), APIPlanContentList.class);
		APIPlan apiPlan = APIPlan.builder().apiPlanNo(dto.getApiPlanNo())
				.apiPlanContentList(contentList).build();
		return apiPlan;
	}
	
	default APIPlanDTO EntitytoDTO(APIPlan apiPlan) throws JsonProcessingException {
		String json = objectMapper.writeValueAsString(apiPlan.getApiPlanContentList());
		APIPlanDTO dto = APIPlanDTO.builder().apiPlanNo(apiPlan.getApiPlanNo())
				.apiPlanContentList(json).build();
		return dto;
	}
	
	int register(APIPlanDTO dto) throws JsonMappingException, JsonProcessingException;
	void remove(int no);
	APIPlanDTO read (int no) throws JsonProcessingException;
	String download(int no) throws JsonProcessingException;
}
