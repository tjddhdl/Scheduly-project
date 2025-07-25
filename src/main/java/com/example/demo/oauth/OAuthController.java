package com.example.demo.oauth;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.GoogleTokenVerifier;
import com.example.demo.security.JWTUtil;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import lombok.RequiredArgsConstructor;



// 프론트 url 경로에 맞춰 수정해야 함 @requestMapping
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OAuthController {

	private final GoogleOAuthService googleOAuthService;
	private final UserRepository userRepository;
	private final JWTUtil jwtUtil;
	@Autowired
	PasswordEncoder passwordEncoder;
	

	@PostMapping("/google")
	public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) throws UnsupportedEncodingException {

		

		try {

			GoogleUser googleUser = googleOAuthService.getGoogleUserInfo(request.getIdToken());
			

			String email = googleUser.getEmail();

			User user = userRepository.findByUserId(email);
			if (user == null) {
				
				user = registerGoogleUser(googleUser);
			}
			String token = jwtUtil.generateToken(email);
			
			return ResponseEntity.ok(
				new JwtResponse(token, user.getUserId(),user.getUserName(),user.getRole().name(),user.getUserNo())	
			);

		} catch (Exception e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("구글 인증 실패");
		}
	}

	private User registerGoogleUser(GoogleUser googleUser) {
		User user = new User();
		user.setUserId(googleUser.getEmail());

	    user.setPassword("");
		user.setRole(Role.free);
		user.setUserName(googleUser.getName());
		
		return userRepository.save(user);
	}
	
}
