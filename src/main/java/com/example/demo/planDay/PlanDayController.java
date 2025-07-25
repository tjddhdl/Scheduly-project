package com.example.demo.planDay;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

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
	
	@GetMapping("planDayList")
	public ResponseEntity<List<PlanDayDto>> getListByPlan(@RequestParam("planNo") int planNo, Principal principal){
		String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
		
		List<PlanDayDto> list = service.getListByPlanNoToAPI(userNo, planNo);
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
		service.dayDetailStatusCheck(Integer.parseInt(map.get("planDayNo").toString()));
	}

	@PostMapping("/addJson")
	public ResponseEntity<PlanDayDetail> planDayAddJson(@RequestBody Map<String, Object> map, Principal principal) {

		
		PlanDayDetail detail = service.addJson(Integer.parseInt(map.get("planDayNo").toString()), map.get("content").toString());
		return ResponseEntity.ok(detail);

	}

	@PostMapping("/removeJson")
	public ResponseEntity<Void> planDayRemoveJson(@RequestBody Map<String, Object> map, Principal principal) {
		int planDayNo = Integer.parseInt(map.get("planDayNo").toString());
		int detailIndex = Integer.parseInt(map.get("detailIndex").toString());
		
		service.removeJson(planDayNo, detailIndex);
		
		if(service.isPlanDayEmpty(planDayNo)) {
			service.removePlanDay(planDayNo);
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/moveJson")
	public void planDayMoveJson(@RequestBody Map<String, Object> map, Principal principal) {
		service.moveJson(Integer.parseInt(map.get("planDayNo").toString()),
				Integer.parseInt(map.get("detailIndex").toString()), map.get("move").toString());
	}
	
	@PostMapping("moveDate")
	public ResponseEntity<String> movePlanDayDate(@RequestBody planDayMoveDateDTO dto) {
		
		try{
			service.movePlanDayDate(dto.getPlanDayNo(), dto.getNewDate());
			return ResponseEntity.ok("성공");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패");
		}
	}

	@PostMapping("/reArray")
	public ResponseEntity<List<PlanDayDto>> reArray(@RequestParam(name = "planNo") int planNo, @RequestParam(name="date") LocalDate date, Principal principal) {
		List<PlanDayDto> list = service.getListByPlanNo(planNo);
		List<PlanDayDto> arraied = service.reArray(list, date);
		return ResponseEntity.ok(arraied);
	}

	
	// 하루씩 밀기
	@PostMapping("/pushDate")
	public ResponseEntity<List<PlanDayDto>> pushDateToList(@RequestParam(name = "planNo") int planNo, @RequestParam(name = "planDayNo") int planDayNo, Principal principal){
		List<PlanDayDto> list = service.pushDateToList(planNo, planDayNo);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/pullDate")
	public ResponseEntity<List<PlanDayDto>> pullDateToList(@RequestParam(name = "planNo") int planNo, @RequestParam(name = "planDayNo") int planDayNo, Principal principal){
		List<PlanDayDto> list = service.pullDateToList(planNo, planDayNo);
		return ResponseEntity.ok(list);
	}

	@PostMapping("/allstatus")
	public ResponseEntity<?> toggleAllTaskStatus(@RequestBody Map<String, Integer> request) {
	    Integer planDayNo = request.get("planDayNo");
	    service.toggleAllStatus(planDayNo);
	    service.dayDetailStatusCheck(planDayNo);
	    return ResponseEntity.ok().build();
	}


}
