package com.example.demo.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.board.Board;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{

	// board로 댓글리스트 찾기
	List<Comment> findByBoard(Board board);
}
