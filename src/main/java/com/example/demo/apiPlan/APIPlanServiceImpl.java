package com.example.demo.apiPlan;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class APIPlanServiceImpl implements APIPlanService{

	@Autowired
	APIPlanRepository apiPlanRepository;
	
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

}
