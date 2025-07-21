package com.example.demo.kakao;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
@RequestMapping("/kakao")
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public KakaoPayController(KakaoPayService kakaoPayService) {
		this.kakaoPayService = kakaoPayService;
	}

	// 결제 준비 요청
	// 프론트에서 post요청하면 redirect url과 tid 반환
	@PostMapping("/ready")
	public ResponseEntity<Map<String, String>> kakaoPayReady(@RequestBody Map<String, Object> payload) {
		int userNo = (int) payload.get("userNo");
		Map<String, String> response = kakaoPayService.readyKakaoPay(userNo);
		return ResponseEntity.ok(response);
	}

	// 결제 승인 요청
	// 결제 성공 시 pg_token과 tid를 쿼리스트림으로 받아 처리
	@PostMapping("/approve")
	public ResponseEntity<Map<String, Object>> kakaoPayApprove(@RequestBody Map<String, String> data) {
		String pgToken = data.get("pg_token");
		String tid = data.get("tid");
		int userNo = Integer.parseInt(data.get("userNo"));
		Map<String, Object> result = kakaoPayService.approveKakaoPay(pgToken, tid, userNo);
		User user = userRepository.findById(userNo).orElseThrow(() -> new RuntimeException("User not found"));

		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserId());

		Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
		Map<String, Object> response = new HashMap<>();
		response.put("paymentResult", result);
		response.put("user", user);
		return ResponseEntity.ok(response);
	}
}
