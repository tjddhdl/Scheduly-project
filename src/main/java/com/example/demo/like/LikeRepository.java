package com.example.demo.like;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.demo.board.Board;
import com.example.demo.user.User;


public interface LikeRepository extends JpaRepository<Like, Integer> {

	Like findByUser(User user);
	
	Like findByUserAndBoard(User user, Board board);
}
