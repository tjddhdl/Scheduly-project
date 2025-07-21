package com.example.demo.board;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.apiPlan.APIPlanDTO;
import com.example.demo.apiPlan.APIPlanService;
import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	APIPlanService apiPlanService;

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
	
	//등록페이지에서 유저가 고를 apiPlan목록을 전달
	@GetMapping("/register")
	public ResponseEntity<List<APIPlanDTO>> getAPIPlanDTOList(Principal principal){
		String userId = principal.getName();
		List<APIPlanDTO> list = apiPlanService.readByUserId(userId);
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Integer> boardRegister(@RequestBody BoardDTO dto, Principal principal){
		int boardNo = boardService.register(dto);
		return ResponseEntity.ok(boardNo);
	}

}
