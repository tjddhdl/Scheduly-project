package com.example.demo.plan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping
public class PlanController {

	@Autowired
	PlanService planService;
	
	// 나중에 react에 맞춰 수정 필요
	// 요구사항에 맞춰 플랜 생성
	@PostMapping("")
	public void asd(@RequestBody String json) {
	
	}
	


}
