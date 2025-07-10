package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	UserService service;

	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestBody UserDto dto) {
		boolean result = service.register(dto);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
