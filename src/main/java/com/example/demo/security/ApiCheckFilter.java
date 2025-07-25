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
		String[] excludePatterns = {
				"/register",
				"/login",
				"/google"
		};
		matcher = new AntPathMatcher();
		
		for(String exclude : excludePatterns) {
			if(matcher.match(exclude, request.getRequestURI())) {
				filterChain.doFilter(request, response);
				return;
			}
		}
		
		// 나중에 추가해야함
		try {
            if (checkAuthHeader(request)) {
                String username = getUserId(request);
                UserDetails details = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            } else {
                SecurityContextHolder.clearContext();
                sendForbidden(response, "유효하지 않은 토큰입니다.");
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            sendForbidden(response, "인증 중 오류가 발생했습니다.");
        }
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
	
	void sendForbidden(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json; charset=utf-8");

        JSONObject json = new JSONObject();
        json.put("code", "403");
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }
}
