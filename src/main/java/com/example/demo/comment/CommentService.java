package com.example.demo.comment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.board.Board;
import com.example.demo.user.User;

public interface CommentService {

	default Comment DtoToEntity(CommentDTO dto) {
		User user = User.builder().userNo(dto.getUser()).build();
		Board board = Board.builder().boardNo(dto.getBoard()).build();
		Comment comment = Comment.builder().commentNo(dto.getCommentNo()).user(user).board(board)
				.commentContent(dto.getCommentContent()).deleteStatus(dto.isDeleteStatus()).build();
		return comment;
	}

	default CommentDTO EntityToDTO(Comment comment) {
		CommentDTO dto = CommentDTO.builder().commentNo(comment.getCommentNo()).user(comment.getUser().getUserNo())
				.board(comment.getBoard().getBoardNo()).commentContent(comment.getCommentContent())
				.userName(comment.getUser().getUserName()).commentTime(comment.getCommentTime())
				.deleteStatus(comment.isDeleteStatus()).build();
		if (comment.getParentComment() != null) {
			dto.setParentCommentNo(comment.getParentComment().getCommentNo());
		}
		List<CommentDTO> childList = comment.getChildComments().stream()
				.sorted(Comparator.comparing(Comment::getCommentTime)).map(this::EntityToDTO)
				.collect(Collectors.toList());
		dto.setChildComments(childList);
		return dto;
	}

	int register(CommentDTO dto);

	// 보드 no로 댓글 전체조회(정렬해서)
	List<CommentDTO> readAll(int no);

	int modify(CommentDTO dto);

	// 개별 삭제일 경우 삭제가 아니라 (삭제됨)으로 정보 교체
	void remove(int no);

	// 보드 삭제할 경우 보드no로 전부 삭제
	void removeAll(int boardNo);
}
