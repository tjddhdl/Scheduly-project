package com.example.demo.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
	private String token;
	private String userId;
    private String userName;
    private String role;
    private int userNo;
    
}
