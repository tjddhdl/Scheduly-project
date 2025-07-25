package com.example.demo.Board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.apiPlan.APIPlan;
import com.example.demo.apiPlan.APIPlanRepository;
import com.example.demo.board.Board;
import com.example.demo.board.BoardRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepositoryTest {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	APIPlanRepository apiPlanRepository;
	
	@Test
	void 보드등록() {
		APIPlan apiPlan = APIPlan.builder().apiPlanNo(4).build();
		Board board = Board.builder().apiPlan(apiPlan).boardName("컴퓨터일반")
				.boardContent("테스트").likeCount(0).build();
		boardRepository.save(board);
	}
	
	@Test
	void 보드삭제() {
		boardRepository.deleteById(5);
	}
	
	@Test
	void 보드조회() {
		Board board = boardRepository.findById(6).get();
		System.out.println(board);
	}
	
	@Test
	void 보드수정() {
		Board board = boardRepository.findById(6).get();
		board.setBoardContent("수정");
		boardRepository.save(board);
	}
	
	@Transactional
	@Test
	void api플랜으로조회() {
		APIPlan apiPlan = apiPlanRepository.findById(2).get();
		Board board = boardRepository.findByApiPlan(apiPlan);
		System.out.println(board);
	}
}
