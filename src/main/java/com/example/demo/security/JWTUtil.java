package com.example.demo.security;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTUtil {
	private String secretKey = "zerock12345678";
	
	// 유효기간 1달
	private long expire = 60 * 24 * 30;
	
	private Set<String> blacklist = new HashSet<>();
	public void invalidateToken(String token) {
		blacklist.add(token);
	}
	public boolean isTokenInvailed(String token) {
		return blacklist.contains(token);
	}
	public String generateToken(String content) throws UnsupportedEncodingException {
		return Jwts.builder().setIssuedAt(new Date())
				.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant())).claim("sub", content)
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes("UTF-8")).compact();
	}
	
	public String validateAndExtract(String tokenStr) {
		String contentValue = null;
		for(String token : blacklist) {
			if(token.equals(tokenStr)) {
				log.info("해당 토큰을 사용할 수 없습니다..");
				return "";
			}
		}
		DefaultJws defaultJws;
		try {
			defaultJws = (DefaultJws) Jwts.parser().setSigningKey(secretKey.getBytes("UTF-8"))
					.parseClaimsJws(tokenStr);
			log.info(defaultJws);
			log.info(defaultJws.getBody().getClass());
			DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
			log.info("------------------------");
			contentValue = claims.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			contentValue = null;
		}
		
		return contentValue;
		
	}
}
