package com.example.demo.security;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.demo.user.UserDto;

public class CustomUser extends User {
	
	public CustomUser(UserDto dto) {
		super(dto.getUserId(),dto.getPassword(),Arrays.asList(new SimpleGrantedAuthority("ROLE_"+dto.getRole())));
	}
}
