package com.example.demo.Board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.apiPlan.APIPlan;
import com.example.demo.board.Board;
import com.example.demo.board.BoardDTO;
import com.example.demo.board.BoardRepository;
import com.example.demo.board.BoardService;

@SpringBootTest
public class BoardServiceTest {

	@Autowired
	BoardService boardService;

	@Autowired
	BoardRepository boardRepository;

	@Test
	void entity를dto로변환() {
		Board board = boardRepository.findById(6).get();
		BoardDTO dto = boardService.EntityToDTO(board);
		System.out.println(dto);
	}

	@Test
	void dto를entity로변환() {
		BoardDTO dto = BoardDTO.builder().apiPlan(1).boardName("변환").boardContent("내용").likeCount(0).build();
		Board board = boardService.dtoToEntity(dto);
		System.out.println(board);
	}

	// apiPlan이 json이 아닐 경우 에러남
	@Test
	void 등록테스트() {
		BoardDTO dto = BoardDTO.builder().apiPlan(2).boardName("테스트").boardContent("테스트").likeCount(0).build();
		boardService.register(dto);
	}

	// apiPlan이 json이 아닐 경우 에러남
	@Test
	void 조회테스트() {
		BoardDTO dto = boardService.read(8);
		System.out.println(dto);
	}
	
	@Test
	void 삭제테스트() {
		boardService.remove(6);
	}
	
	@Test
	void page() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<BoardDTO> page = boardService.findAll(pageable);
		for(BoardDTO dto : page) {
			System.out.println(dto);
		}
	}
}
