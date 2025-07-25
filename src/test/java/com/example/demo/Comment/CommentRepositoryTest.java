package com.example.demo.Comment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.Board;
import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentRepository;
import com.example.demo.user.User;

@SpringBootTest
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	@Test
	void 코멘트등록() {
		User user = User.builder().userNo(1).build();
		Board board = Board.builder().boardNo(8).build();
		Comment comment = Comment.builder().user(user)
				.board(board).commentContent("테스트2번")
				.build();
		commentRepository.save(comment);
	}
	
	@Test
	void 대댓글등록() {
		User user = User.builder().userNo(1).build();
		Board board = Board.builder().boardNo(8).build();
		Comment parent = commentRepository.findById(2).get();
		Comment comment = Comment.builder().user(user)
				.board(board).commentContent("2번의 대댓글")
				.parentComment(parent).build();
		commentRepository.save(comment);
	}
	
	@Test
	void 코멘트boardNo로조회() {
		Board board = Board.builder().boardNo(8).build();
		List<Comment> list = commentRepository.findByBoard(board);
		for(Comment comment : list) {
			System.out.println(comment.getCommentContent());
		}
	}
	
	@Test
	void 코멘트수정() {
		Comment comment = commentRepository.findById(1).get();
		comment.setCommentContent("뗶뗶뗶뗶뗶");
		commentRepository.save(comment);
	}
	
	// 대댓글이 달려있을 경우엔 삭제가 안됨
	// 컬럼을 날리는게 아니라 코멘트의 정보만 날리기(삭제된 댓글입니다)
	// 코멘트 삭제 기능은 게시판을 삭제할 경우에 댓글 정보까지 날리는 상황에서
	// 쓰이는 방향으로 변경
	@Test
	void 코멘트삭제() {
		commentRepository.deleteById(1);
	}
	
	@Test
	void 코멘트전체삭제() {
		Board board = Board.builder().boardNo(8).build();
		List<Comment> list = commentRepository.findByBoard(board);
		list.sort(Comparator.comparingInt(c->getDepth(c)));
		for(Comment comment : list) {
			commentRepository.delete(comment);
		}
	}
	private int getDepth(Comment comment) {
		int depth = 0;
		while(comment.getParentComment()!=null) {
			comment = comment.getParentComment();
			depth++;
		}
		return depth;
	}
}
