package com.example.demo.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	@Override
	public int register(BoardDTO dto) {
		Board board = dtoToEntity(dto);
		boardRepository.save(board);
		return board.getBoardNo();
	}

	@Override
	public void remove(int no) {
		boardRepository.deleteById(no);
	}

	@Override
	public BoardDTO read(int no) {
		Optional<Board> optional = boardRepository.findById(no);
		if(optional.isPresent()) {
			Board board = optional.get();
			BoardDTO boardDTO = EntityToDTO(board);
			return boardDTO;
		}
		return null;
	}

	@Override
	public int modify(BoardDTO dto) {
		Optional<Board> optional = boardRepository.findById(dto.getBoardNo());
		if(optional.isPresent()) {
			Board old = optional.get();
			Board change = dtoToEntity(dto);
			if(change.getBoardName()!=null) {
				old.setBoardName(change.getBoardName());
			}
			if(change.getBoardContent()!=null) {
				old.setBoardContent(change.getBoardContent());
			}
			boardRepository.save(old);
			return old.getBoardNo();
		}
		return 0;
	}

	@Override
	public Page<BoardDTO> findAll(Pageable pageable) {
		Page<Board> list = boardRepository.findAll(pageable);
		Page<BoardDTO> page = list.map(e->EntityToDTO(e));
		return page;
	}

}
