package com.example.demo.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PlanController {

	@Autowired
	PlanService planService;
	
	// 나중에 react에 맞춰 수정 필요
	// 요구사항에 맞춰 플랜 생성
	@PostMapping("")
	public void asd(@RequestBody String json) {
	
	}
}
