package com.example.demo.Like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.Board;
import com.example.demo.like.Like;
import com.example.demo.like.LikeRepository;
import com.example.demo.user.User;

@SpringBootTest
public class LikeRepositoryTest {

	@Autowired
	LikeRepository likeRepository;

	@Test
	void 조회() {
		User user = User.builder().userNo(3).build();
		Board board = Board.builder().boardNo(14).build();
		Like like = likeRepository.findByUserAndBoard(user, board);
		System.out.println(like);
	}
}
