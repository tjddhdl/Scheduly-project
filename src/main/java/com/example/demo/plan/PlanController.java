package com.example.demo.plan;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.planDay.PlanDayDto;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/api")
    public ResponseEntity<Integer> registerFromGPT(
            Principal principal,
            @RequestBody String gptJson) {
    	String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
        int planNo = planService.registerAPI(userNo, gptJson);
        return ResponseEntity.ok(planNo);
        
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<PlanDto>> getList(Principal principal) {
    	
    	String userId = principal.getName();
    	User user = userRepository.findByUserId(userId);
    	int userNo = user.getUserNo();
    	
    	List<PlanDto> dto = planService.getList(userNo);
    	return ResponseEntity.ok(dto);
    }
    

    @PostMapping("/removePlan")
    public BodyBuilder removePlan(Principal principal, @RequestParam int planNo){
    	planService.remove(planNo);
    	return ResponseEntity.accepted();
    }
    
    @PostMapping("/modifyPlan")
    public BodyBuilder modifyPlan(Principal principal, @RequestParam int planNo, @RequestParam String planName) {
    	PlanDto planDto = planService.read(planNo);
    	planDto.setPlanName(planName);
    	planService.register(planDto);
    	return ResponseEntity.accepted();
    }

    @PostMapping("/modify")
	public ResponseEntity<Boolean> planModify(@RequestBody PlanDto dto, Principal principal) {
		planService.modify(dto);
		return ResponseEntity.ok(true);
	}
    
    @GetMapping("/{planNo}")
    public ResponseEntity<Map<String, Object>> getPlanName(@PathVariable("planNo") int planNo) {
        PlanDto planDto = planService.read(planNo);
        if (planDto == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("planNo", planDto.getPlanNo());
        result.put("planName", planDto.getPlanName());
        result.put("planStatus", planDto.getStatus());
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/{planNo}/complete")
    public ResponseEntity<?> completePlanIfAllTasksFinished(@PathVariable("planNo") int planNo) {
        boolean updated = planService.updateStatusIfAllTasksFinished(planNo);
        if (updated) {
            return ResponseEntity.ok("플랜 상태 완료로 변경됨");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아직 완료되지 않은 할일이 존재합니다.");
        }
    }



}

