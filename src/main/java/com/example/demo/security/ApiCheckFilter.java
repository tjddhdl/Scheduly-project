package com.example.demo.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;

public class ApiCheckFilter extends OncePerRequestFilter {

	AntPathMatcher matcher;
	JWTUtil jwtUtil;
	UserDetailsService userDetailsService;
	
	public ApiCheckFilter(UserDetailsService detailsService) {
		jwtUtil = new JWTUtil();
		this.userDetailsService = detailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 나중에 추가해야함
		String[] patternArr = {""};
		matcher = new AntPathMatcher();
		for(String patt : patternArr) {
			boolean result = matcher.match(patt, request.getRequestURI());
			if(result) {
				boolean check = checkAuthHeader(request);
				if(check) {
					String username = getUserId(request);
					UserDetails details = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
					WebAuthenticationDetails webDetails =
							new WebAuthenticationDetailsSource().buildDetails(request);
					authToken.setDetails(webDetails);
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
					return;
				} else {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setContentType("application/json; charset=utf=8");
					JSONObject json = new JSONObject();
					json.put("code", "403");
					json.put("message", "토큰을 넣어주세요");
					PrintWriter out = response.getWriter();
					out.print(json);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	
	boolean checkAuthHeader(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if(auth!=null) {
			String id = null;
			id = jwtUtil.validateAndExtract(auth);
			if(id!=null) {
				return true;
			}
		}
		return false;
	}

	String getUserId(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if(auth!=null) {
			String id = jwtUtil.validateAndExtract(auth);
			return id;
		}
		return null;
	}
}
