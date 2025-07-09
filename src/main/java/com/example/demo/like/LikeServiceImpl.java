package com.example.demo.like;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
