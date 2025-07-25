package com.example.demo.comment;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;

	@Autowired
	UserService userService;

	@GetMapping("/get")
	public ResponseEntity<List<CommentDTO>> getCommentList(Principal principal,
			@RequestParam(name = "boardNo") int boardNo) {
		List<CommentDTO> list = commentService.readAll(boardNo);
		return ResponseEntity.ok(list);
	}

	@PostMapping("/register")
	public ResponseEntity<Object> postComment(Principal principal, @RequestBody Map<String, Object> map) {
		CommentDTO dto = CommentDTO.builder().board(Integer.parseInt(map.get("board").toString()))
				.commentContent(map.get("commentContent").toString()).build();
		if(map.get("parentCommentNo")!=null) {
			dto.setParentCommentNo(Integer.parseInt(map.get("parentCommentNo").toString()));
		}
		UserDto userDto = userService.read(principal.getName());
		dto.setUser(userDto.getUserNo());
		dto.setDeleteStatus(false);
		commentService.register(dto);
		return ResponseEntity.accepted().build();
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Object> postDelete(Principal principal, @RequestBody int commentNo){
		commentService.remove(commentNo);
		return ResponseEntity.accepted().build();
	}

}
