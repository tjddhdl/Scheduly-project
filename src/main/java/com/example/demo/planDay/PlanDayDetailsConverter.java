package com.example.demo.planDay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PlanDayDetailsConverter implements AttributeConverter<List<PlanDayDetail>, String>{

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public String convertToDatabaseColumn(List<PlanDayDetail> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("JSON 직렬화 실패", e);
		}
	}

	@Override
	public List<PlanDayDetail> convertToEntityAttribute(String dbData) {
		try {
			if(dbData == null || dbData.isEmpty()) return new ArrayList<>();
			return objectMapper.readValue(dbData, new TypeReference<>() {});
		} catch (IOException e) {
			throw new IllegalArgumentException("JSON 역직렬화 실패", e);
		}
	}
	
	
}
