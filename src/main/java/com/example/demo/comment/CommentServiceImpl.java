package com.example.demo.comment;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

	@Override
	public int register(CommentDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CommentDTO> readAll(int no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modify(CommentDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void remove(int no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll(int boardNo) {
		// TODO Auto-generated method stub
		
	}

}
