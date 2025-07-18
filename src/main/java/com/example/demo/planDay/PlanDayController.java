package com.example.demo.planDay;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
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

	@PostMapping("/modify")
	public ResponseEntity<Boolean> planDayModify(@RequestParam PlanDayDto dto, Principal principal) {
		service.modify(dto);
		return ResponseEntity.ok(true);
	}

	@PostMapping("/jsonmodify")
	public ResponseEntity<PlanDayDetail> planDayJsonModify(@RequestBody Map<String, Object> map, Principal principal) {
		PlanDayDetail detail = service.jsonModify(Integer.parseInt(map.get("planDayNo").toString()),
				Integer.parseInt(map.get("detailIndex").toString()), map.get("detail").toString());
		return ResponseEntity.ok(detail);
	}

	@PostMapping("/jsonstatus")
	public void planDayJsonStatus(@RequestBody Map<String, Object> map, Principal principal) {
		service.statusChange(Integer.parseInt(map.get("planDayNo").toString()),
				Integer.parseInt(map.get("detailIndex").toString()));
	}

	@PostMapping("/addJson")
	public ResponseEntity<PlanDayDetail> planDayAddJson(@RequestBody Map<String, Object> map, Principal principal) {

		
		PlanDayDetail detail = service.addJson(Integer.parseInt(map.get("planDayNo").toString()), map.get("content").toString());
		return ResponseEntity.ok(detail);

	}

	@PostMapping("/removeJson")
	public void planDayRemoveJson(@RequestBody Map<String, Object> map, Principal principal) {
		service.removeJson(Integer.parseInt(map.get("planDayNo").toString()),
				Integer.parseInt(map.get("detailIndex").toString()));
	}

	@PostMapping("/moveJson")
	public void planDayMoveJson(@RequestBody Map<String, Object> map, Principal principal) {
		service.moveJson(Integer.parseInt(map.get("planDayNo").toString()),
				Integer.parseInt(map.get("detailIndex").toString()), map.get("move").toString());
	}
}
