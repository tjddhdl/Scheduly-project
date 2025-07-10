package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public boolean register(UserDto dto) {
		if(userRepository.findByUserId(dto.getUserId())!=null) {
			dto.setRole("free");
			User user = dtoToEntity(dto);
			String enpw = encoder.encode(user.getPassword());
			System.out.println(user);
			user.setPassword(enpw);
			userRepository.save(user);
			return true;
		}else {
			return false;			
		}
	}

	@Override
	public UserDto read(String userId) {
		User user = userRepository.findByUserId(userId);
		if(user!=null) {
			return entityToDto(user);
		}
		return null;
	}

	
	
}
