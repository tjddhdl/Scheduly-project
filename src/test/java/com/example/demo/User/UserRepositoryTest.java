package com.example.demo.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;
	
	@Test
	void 유저조회() {
		User user = repository.findById(1).get();
		System.out.println(user);
	}
}
