package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUserId(String userId);
	
	User findByUserNo(int userNo);
}
