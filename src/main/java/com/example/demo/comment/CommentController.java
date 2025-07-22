package com.example.demo.comment;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@GetMapping("/get")
	public ResponseEntity<List<CommentDTO>> getCommentList(Principal principal, @RequestParam(name="boardNo") int boardNo){
		List<CommentDTO> list = commentService.readAll(boardNo);
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
	
	
}
