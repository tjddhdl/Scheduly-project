package com.example.demo.Comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentDTO;
import com.example.demo.comment.CommentRepository;
import com.example.demo.comment.CommentService;

@SpringBootTest
public class CommentServiceTest {

	@Autowired
	CommentService commentService;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Test
	void entityDTO로변환() {
		Comment comment = commentRepository.findById(2).get();
		CommentDTO dto = commentService.EntityToDTO(comment);
		System.out.println(dto);
	}
	
	@Test
	void DtoEntity로변환() {
		CommentDTO dto = new CommentDTO(3, 1, 6, "엘렐레", null);
		Comment comment = commentService.DtoToEntity(dto);
		System.out.println(comment);
	}
}
