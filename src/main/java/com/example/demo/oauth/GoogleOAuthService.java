package com.example.demo.oauth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class GoogleOAuthService {

//	private final RestTemplate restTemplate = new RestTemplate();
//
//	public GoogleUser getGoogleUserInfo(String accessToken) {
//		log.info("구글 유저 정보 요청 - AccessToken: {}", accessToken);
//		String url = "https://www.googleapis.com/oauth2/v3/userinfo";
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setBearerAuth(accessToken);
//
//		HttpEntity<Void> request = new HttpEntity<>(headers);
//		ResponseEntity<GoogleUser> response = restTemplate.exchange(url, HttpMethod.GET, request, GoogleUser.class);
//		log.info("구글 API 응답: {}", response.getBody());
//		return response.getBody();
//	}
	
	private static final String CLIENT_ID = "875926317578-ia85hct28ujlq45hct4an2dugorro69g.apps.googleusercontent.com";
	

    public GoogleUser getGoogleUserInfo(String idTokenString) throws GeneralSecurityException, IOException {
    	GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            // 필요한 경우 이름, 사진 URL 등도 payload에서 가져올 수 있음
            String name = (String) payload.get("name");

            GoogleUser user = new GoogleUser();
            user.setEmail(email);
            user.setName(name);

            return user;
        } else {
            throw new RuntimeException("Invalid ID token.");
        }
    }
}
