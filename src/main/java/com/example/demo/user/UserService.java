package com.example.demo.user;

import com.example.demo.oauth.GoogleUser;

public interface UserService {

	boolean register(UserDto dto);

	UserDto read(String userId);
	
	boolean isUserIdExist(String userId);
	
	default UserDto entityToDto(User user) {
		UserDto dto = UserDto.builder().userNo(user.getUserNo()).userId(user.getUserId()).password(user.getPassword())
				.userName(user.getUserName()).role(user.getRole().toString()).build();

		return dto;
	}

	default User dtoToEntity(UserDto dto) {
		User user = User.builder().userNo(dto.getUserNo()).userId(dto.getUserId()).password(dto.getPassword())
				.userName(dto.getUserName()).role(Role.valueOf(dto.getRole())).build();

		return user;
	}
}
