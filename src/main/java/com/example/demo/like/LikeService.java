package com.example.demo.like;

import com.example.demo.board.Board;
import com.example.demo.user.User;

public interface LikeService {
	int register(LikeDto dto);
	void remove(int likeNo);
	
	LikeDto readByUserNo(int userNo);
	
	default LikeDto entityToDto(Like like) {
		int user = like.getUser().getUserNo();
		int board = like.getBoard().getBoardNo();
		
		LikeDto dto = LikeDto.builder()
									.likeNo(like.getLikeNo())
									.userNo(user)
									.boardNo(board)
									.build();
		return dto;
	}
	
	default Like dtoToEntity(LikeDto dto) {
		User user = User.builder().userNo(dto.getUserNo()).build();
		Board board = Board.builder().boardNo(dto.getBoardNo()).build();
		
		Like like = Like.builder()
								.likeNo(dto.getLikeNo())
								.user(user)
								.board(board)
								.build();
		return like;
	}
}
