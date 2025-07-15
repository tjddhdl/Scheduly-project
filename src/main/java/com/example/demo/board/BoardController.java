package com.example.demo.board;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	@GetMapping("/main")
	public ResponseEntity<Map<String, Object>> getBoard(@RequestParam(name="page", defaultValue = "0") int page,
			Principal principal) {
		Pageable pageable = PageRequest.of(page, 10);
		Page<BoardDTO> pageList = boardService.findAll(pageable);
		Map<String, Object> resMap = new HashMap<>();
		resMap.put("list", pageList.getContent());
		resMap.put("totalPage", pageList.getTotalPages());
		resMap.put("currentPage", pageList.getNumber());
		return ResponseEntity.ok(resMap);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<BoardDTO> getBoardDetail(@RequestParam(name="boardNo") int boardNo, Principal principal){
		BoardDTO dto = boardService.read(boardNo);
		return ResponseEntity.ok(dto);
	}

}
