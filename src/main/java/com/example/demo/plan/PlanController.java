package com.example.demo.plan;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

