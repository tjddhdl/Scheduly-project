package com.example.demo.kakao;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
@RequestMapping("/kakao")
public class KakaoPayController {

	private final KakaoPayService kakaoPayService;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	public KakaoPayController(KakaoPayService kakaoPayService) {
		this.kakaoPayService = kakaoPayService;
	}

	// 결제 준비 요청
	// 프론트에서 post요청하면 redirect url과 tid 반환
	@PostMapping("/ready")
	public ResponseEntity<Map<String, String>> kakaoPayReady() {
		Map<String, String> response = kakaoPayService.readyKakaoPay();
		return ResponseEntity.ok(response);
	}
	// 결제 승인 요청
	// 결제 성공 시 pg_token과 tid를 쿼리스트림으로 받아 처리
	@GetMapping("/approve")
	public ResponseEntity<Map<String, Object>> kakaoPayApprove(@RequestParam("pg_token") String pgToken,
			@RequestParam("tid") String tid, Principal principal) {
		String userId = principal.getName();
		User user = userRepository.findByUserId(userId);
		int userNo = user.getUserNo();
		Map<String, Object> result = kakaoPayService.approveKakaoPay(pgToken, tid, userNo);
		return ResponseEntity.ok(result);
	}
}
