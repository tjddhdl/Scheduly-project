package com.example.demo.oauth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

	private final RestTemplate restTemplate = new RestTemplate();

	public GoogleUser getGoogleUserInfo(String accessToken) {
		String url = "https://www.googleapis.com/oauth2/v3/userinfo";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);
		ResponseEntity<GoogleUser> response = restTemplate.exchange(url, HttpMethod.GET, request, GoogleUser.class);

		return response.getBody();
	}
}
