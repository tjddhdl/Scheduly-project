package com.example.demo.security;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component는 CORSFilter를 빈으로 등록하는 어노테이션
// 자동으로 CORS 필터가 필터체인에 추가됨
@Component
// 필터의 순서 변경: 다른 필터보다 먼저 등록
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter extends OncePerRequestFilter {
	
	// doFilterInternal: 필터에서 수행할 작업
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// 모든 도메인에서 들어오는 요청을 허용 (localhost:3000 포힘)
		response.setHeader("Access-Control-Allow-Origin", "*"); // 특정 주소만 허용할꺼면 * 대신 특정주소
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// 모든 메소드 요청을 허용 (GET POST PUT DELETE)
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
		
		// OPTIONS 메소드 요청 (사전요청)이 들어오면 무조건 200 OK 코드로 응답
		// 사전 요청: 브라우저가 본 요청을 보내기 전에, 확인용으로 보내는 요청
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			// 그외 (POST PUT DELETE GET)은 다음 단계로 진행
			filterChain.doFilter(request, response);
		}

	}
}