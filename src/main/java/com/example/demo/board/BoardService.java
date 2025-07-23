package com.example.demo.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.apiPlan.APIPlan;

public interface BoardService {
	default Board dtoToEntity(BoardDTO dto) {
		APIPlan apiPlan = APIPlan.builder().apiPlanNo(dto.getApiPlan()).build();
		Board board = Board.builder().boardNo(dto.getBoardNo()).apiPlan(apiPlan).boardName(dto.getBoardName()).boardContent(dto.getBoardContent())
				.likeCount(dto.getLikeCount()).build();
		return board;
	}

	default BoardDTO EntityToDTO(Board board) {
		BoardDTO dto = BoardDTO.builder().boardNo(board.getBoardNo()).apiPlan(board.getApiPlan().getApiPlanNo())
				.boardName(board.getBoardName()).boardContent(board.getBoardContent()).likeCount(board.getLikeCount())
				.userName(board.getApiPlan().getUser().getUserName())
				.boardTime(board.getBoardTime()).build();
		return dto;
	}
	
	int register(BoardDTO dto);
	void remove(int no);
	BoardDTO read(int no);
	int modify(BoardDTO dto);
	Page<BoardDTO> findAll(Pageable pageable);
}
