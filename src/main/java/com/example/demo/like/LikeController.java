package com.example.demo.like;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.board.BoardDTO;
import com.example.demo.board.BoardService;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;

@RestController
public class LikeController {

	@Autowired
	LikeService likeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BoardService boardService;
	
	@PostMapping("/like")
	public ResponseEntity<Integer> like(Principal principal, @RequestParam int boardNo) {
		UserDto dto = userService.read(principal.getName());
		LikeDto likeDto = likeService.readByUserNo(dto.getUserNo());
		if(likeDto!=null) {
			likeService.remove(likeDto.getLikeNo());
			BoardDTO boardDTO = boardService.read(boardNo);
			boardDTO.setLikeCount(boardDTO.getLikeCount()-1);
			boardService.register(boardDTO);
			return ResponseEntity.ok(boardDTO.getLikeCount());
		} else {
			likeService.register(likeDto);
			BoardDTO boardDTO = boardService.read(boardNo);
			boardDTO.setLikeCount(boardDTO.getLikeCount()+1);
			boardService.register(boardDTO);
			return ResponseEntity.ok(boardDTO.getLikeCount());
		}
	}
}
