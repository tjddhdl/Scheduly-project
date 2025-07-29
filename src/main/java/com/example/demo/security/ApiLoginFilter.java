package com.example.demo.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;

public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	JWTUtil jwtUtil;
	UserService userService;
	
	public ApiLoginFilter(String defaltFilterProcessesUrl, UserService userService) {
		super(defaltFilterProcessesUrl);
		this.userService = userService;
		jwtUtil = new JWTUtil();
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String body = getBody(request);
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> map = mapper.readValue(body, HashMap.class);
		
		String id = map.get("userId");
		String password = map.get("password");
		if(id==null) {
			throw new BadCredentialsException("id가 없습니다");
		}
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password);
		return getAuthenticationManager().authenticate(token);
	}
	
	public String getBody(HttpServletRequest request) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = request.getReader();
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		return stringBuilder.toString();
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String id = authResult.getName();
		String token = null;
		token = jwtUtil.generateToken(id);
		
		UserDto dto = userService.read(id);
		HashMap<String, Object> data = new HashMap<>();
		
		data.put("token", token);
		data.put("user", dto);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		ObjectMapper mapper = new ObjectMapper()
			.registerModule(new JavaTimeModule())
			.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
		
		String result = mapper.writeValueAsString(data);
		PrintWriter out = response.getWriter();
		out.print(result);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json; charset=utf-8");
		
		JSONObject json = new JSONObject();
		json.put("code", "401");
		if (failed instanceof UsernameNotFoundException) {
	        json.put("message", "아이디가 존재하지 않습니다.");
	    } else if (failed instanceof BadCredentialsException) {
	        json.put("message", "비밀번호가 틀렸습니다.");
	    } else {
	        json.put("message", failed.getMessage());
	    }
		
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	
	
	
}
