package com.example.demo.gpt;

import java.security.Principal;

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
	
	@PostMapping("/createPlan")
	public ResponseEntity<Integer> createPlan(@RequestBody String userOrder, Principal principal){
		String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
		String planJson = service.createPlan(userOrder);
		
		int planNo = planService.registerAPI(userNo, planJson);
		return ResponseEntity.ok(planNo);
	}
}
