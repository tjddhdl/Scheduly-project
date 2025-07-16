package com.example.demo.gpt;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.plan.PlanService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
@RequestMapping("/gpt")
public class GptController {
	
	@Autowired
	GptService service;
	@Autowired
	PlanService planService;
	@Autowired
	UserRepository userRepository;
	
	// 플랜 생성만
	@PostMapping("/createPlan")
	public ResponseEntity<String> createPlan(@RequestBody String userOrder, Principal principal){
		String planJson = service.createPlan(userOrder);
		return ResponseEntity.ok(planJson);
	}

	// 플랜 수정
	@PostMapping("/fixPlan")
	public ResponseEntity<String> fixPlan(@RequestBody() Map<String, String> map, Principal principal){
		String gptJson = map.get("gptJson");
		String userOrder = map.get("userOrder");
		
		String planJson = service.convertPlan(gptJson, userOrder);
		return ResponseEntity.ok(planJson);
	}
	
	// 플랜 저장만
	@PostMapping("/savePlan")
	public int savePlan(@RequestBody String gptJson, Principal principal){
		String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
		int planNo = planService.registerAPI(userNo, gptJson);
		return planNo;
	}
}
