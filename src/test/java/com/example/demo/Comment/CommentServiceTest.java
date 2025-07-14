package com.example.demo.Comment;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.Board;
import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentDTO;
import com.example.demo.comment.CommentRepository;
import com.example.demo.comment.CommentService;
import com.example.demo.user.User;

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
	void entityToDTO코멘트만들어서테스트() {
		Board board = Board.builder().boardNo(8).build();
		User user = User.builder().userNo(1).build();
		Comment reComment = Comment.builder().commentNo(2).user(user).board(board).commentContent("대댓글")
				.deleteStatus(false).build();
		List<Comment> childComments = new ArrayList<>();
		childComments.add(reComment);

		Comment comment1 = Comment.builder().commentNo(1).board(board).commentContent("1번").deleteStatus(false)
				.user(user).childComments(childComments).build();
		reComment.setParentComment(comment1);
		System.out.println(commentService.EntityToDTO(comment1));
	}

	@Test
	void DtoEntity로변환() {
		CommentDTO dto = new CommentDTO(3, 1, 6, "엘렐레", null, 3, false, null);
		Comment comment = commentService.DtoToEntity(dto);
		System.out.println(comment);
	}

	@Test
	void 코멘트등록() throws InterruptedException {
		CommentDTO dto;
		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 2").parentCommentNo(1).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 3").parentCommentNo(1).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 4").parentCommentNo(2).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 5").parentCommentNo(2).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 6").parentCommentNo(3).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 7").parentCommentNo(4).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 8").parentCommentNo(5).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 9").parentCommentNo(5).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 10").parentCommentNo(7).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 11").parentCommentNo(6).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 12").parentCommentNo(6).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 13").parentCommentNo(11).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 14").parentCommentNo(11).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

		dto = CommentDTO.builder().user(1).board(8).commentContent("댓글 15").parentCommentNo(14).deleteStatus(false).build();
		commentService.register(dto);
		Thread.sleep(1000);

	}
	
	@Test
	void 코멘트조회() {
		List<CommentDTO> list = commentService.readAll(8);
		System.out.println(list);
	}
}
