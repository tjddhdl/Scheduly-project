package com.example.demo.apiPlan;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
@RequestMapping("Api")
public class ApiPlanController {
	
	@Autowired
	APIPlanService service;
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/{planNo}")
	public ResponseEntity<String> uploadPlan(@PathVariable("planNo") int planNo, Principal principal){
		
		String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
		
		
		boolean result = service.upload(planNo,userNo);
		
		if(result) {
			return ResponseEntity.ok("플랜 내보내기 성공");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("플랜을 찾을 수 없음");
		}
	}
	
}
