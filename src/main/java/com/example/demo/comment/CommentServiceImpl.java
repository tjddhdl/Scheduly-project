package com.example.demo.comment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.Board;
import com.example.demo.board.BoardRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	BoardRepository boardRepository;

	@Override
	public int register(CommentDTO dto) {
		dto.setDeleteStatus(false);
		Comment comment = DtoToEntity(dto);
		if (dto.getParentCommentNo() != null) {
			Optional<Comment> optional = commentRepository.findById(dto.getParentCommentNo());
			if (optional.isPresent()) {
				Comment parent = optional.get();
				comment.setParentComment(parent);
			}
		}
		commentRepository.save(comment);
		return comment.getCommentNo();
	}

	@Override
	@Transactional
	public List<CommentDTO> readAll(int no) {

		Optional<Board> optional = boardRepository.findById(no);
		if (optional.isPresent()) {

			Board board = optional.get();
			List<Comment> all = commentRepository.findByBoard(board);
			Map<Integer, CommentDTO> dtoMap = new HashMap<>();
			List<CommentDTO> roots = new ArrayList<>();
			Set<CommentDTO> set = new HashSet<>();
			for (Comment comment : all) {
				comment.getChildComments().size();
				dtoMap.put(comment.getCommentNo(), EntityToDTO(comment));
			}
			for (Comment comment : all) {
				CommentDTO dto = dtoMap.get(comment.getCommentNo());
				if (comment.getParentComment() != null) {
					CommentDTO parent = dtoMap.get(comment.getParentComment().getCommentNo());
					if (!set.add(dto)) {
						parent.getChildComments().add(dto);
					}
				} else {
					roots.add(dto);
				}
			}
			return roots;
		}
		return null;
	}

	@Override
	public int modify(CommentDTO dto) {
		Comment comment = commentRepository.findById(dto.getCommentNo()).get();
		comment.setCommentContent(dto.getCommentContent());
		commentRepository.save(comment);
		return dto.getCommentNo();
	}

	@Override
	public void remove(int no) {
		Comment comment = commentRepository.findById(no).get();
		comment.setDeleteStatus(true);
		commentRepository.save(comment);
	}

	@Override
	public void removeAll(int boardNo) {
		Board board = Board.builder().boardNo(8).build();
		List<Comment> list = commentRepository.findByBoard(board);
		list.sort(Comparator.comparingInt(c -> getDepth(c)));
		for (Comment comment : list) {
			commentRepository.delete(comment);
		}
	}

	private int getDepth(Comment comment) {
		int depth = 0;
		while (comment.getParentComment() != null) {
			comment = comment.getParentComment();
			depth++;
		}
		return depth;
	}
}
