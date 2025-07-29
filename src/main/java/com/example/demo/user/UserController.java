package com.example.demo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserService service;
	@Autowired
	UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDto dto) {
		boolean result = service.register(dto);
		if (result) {
	        return ResponseEntity.status(HttpStatus.CREATED).build();
	    } else {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "이미 존재하는 아이디입니다."));
	    }
	}
	

}
