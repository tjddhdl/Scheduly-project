package com.example.demo.planDay;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
@RequestMapping("/")
public class PlanDayController {
	@Autowired
	PlanDayService service;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/list")
	public ResponseEntity<List<PlanDayDto>> getList(Principal principal) {
		
		String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
		
		List<PlanDayDto> list = service.getList(userNo);
		
		return ResponseEntity.ok(list);
	}
	
	
	
	
}
