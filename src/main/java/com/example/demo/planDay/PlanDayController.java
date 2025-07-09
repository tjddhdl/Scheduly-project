package com.example.demo.planDay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PlanDayController {
	@Autowired
	PlanDayService service;
	
	@GetMapping("/list")
	public ResponseEntity<List<PlanDayDto>> getList(@RequestParam("no") int no) {
		
		
		
		List<PlanDayDto> list = service.getList(1);
		
		return ResponseEntity.ok(list);
	}
	
}
