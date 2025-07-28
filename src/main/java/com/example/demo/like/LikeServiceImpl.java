package com.example.demo.like;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.Board;
import com.example.demo.user.User;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	LikeRepository repository;

	@Override
	public int register(LikeDto dto) {
		Like like = dtoToEntity(dto);

		repository.save(like);
		int newNo = like.getLikeNo();
		return newNo;
	}

	@Override
	public void remove(int likeNo) {

		Optional<Like> optional = repository.findById(likeNo);
		if (optional.isPresent()) {
			repository.deleteById(likeNo);
		}

	}

	@Override
	public LikeDto readByUserNo(int userNo) {
		User user = User.builder().userNo(userNo).build();
		Like like = repository.findByUser(user);
		if (like != null) {
			LikeDto dto = entityToDto(like);
			return dto;
		} else {
			return null;
		}
	}

	@Override
	public LikeDto findByUserAndBoard(int userNo, int boardNo) {
		User user = User.builder().userNo(userNo).build();
		Board board = Board.builder().boardNo(boardNo).build();
		Like like = repository.findByUserAndBoard(user, board);
		if (like != null) {
			LikeDto likeDto = entityToDto(like);
			return likeDto;
		} else {
			return null;
		}
	}

}
