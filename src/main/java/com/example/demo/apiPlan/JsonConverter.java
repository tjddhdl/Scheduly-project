package com.example.demo.apiPlan;

import com.example.demo.apiPlan.APIPlan.APIPlanContentList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonConverter implements AttributeConverter<APIPlan.APIPlanContentList, String>{

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(APIPlan.APIPlanContentList attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("JSON 직렬화 실패", e);
		}
	}

	@Override
	public APIPlanContentList convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, APIPlan.APIPlanContentList.class);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("JSON 역직렬화 실패", e);
		}
	}
	
	
}
