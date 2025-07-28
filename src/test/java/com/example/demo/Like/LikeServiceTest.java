package com.example.demo.Like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.Board;
import com.example.demo.like.LikeDto;
import com.example.demo.like.LikeService;
import com.example.demo.user.User;

@SpringBootTest
public class LikeServiceTest {
	@Autowired
	LikeService service;
	
	@Test
	void 생성() {
		LikeDto dto = LikeDto.builder().userNo(1).boardNo(1).build();
		service.register(dto);
	}
	@Test
	void 삭제() {
		service.remove(1);
	}
}
