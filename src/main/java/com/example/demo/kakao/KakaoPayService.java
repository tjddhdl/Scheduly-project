package com.example.demo.kakao;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;



@Service
public class KakaoPayService {

    @Value("${kakao.admin-key}")
    private String adminKey;

    private final String CID = "TC0ONETIME";

    private final UserRepository userRepository;

    // 생성자 주입
    public KakaoPayService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> readyKakaoPay(int userNo) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + adminKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", CID);
        params.add("partner_order_id", "order123");
        params.add("partner_user_id", "user123");
        params.add("item_name", "테스트 상품");
        params.add("quantity", "1");
        params.add("total_amount", "1000");
        params.add("vat_amount", "100");
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:3000/pay/success");
        params.add("cancel_url", "http://localhost:3000/pay/cancel");
        params.add("fail_url", "http://localhost:3000/pay/fail");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/payment/ready", request, Map.class);
        
        Map<String, Object> responseBody = response.getBody();
        Map<String, String> result = new java.util.HashMap<>();

        if (responseBody != null) {
            result.put("tid", (String) responseBody.get("tid"));
            result.put("next_redirect_pc_url", (String) responseBody.get("next_redirect_pc_url"));
        }

        return result;
    }
    
    @Transactional
    public Map<String, Object> approveKakaoPay(String pgToken, String tid, int userNo) {
    	System.out.println(pgToken);
    	System.out.println(tid);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + adminKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", CID);
        params.add("tid", tid);
        params.add("partner_order_id", "order123");
        params.add("partner_user_id", "user123");
        params.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://kapi.kakao.com/v1/payment/approve", request, Map.class);

        // 결제 승인 후 role 변경 로직
        Map<String, Object> body = response.getBody();

        // 예시: 결제 성공 여부 확인 (status, or 기타 확인 필드 필요)
        boolean success = true; 

        if (success) {
            User user = userRepository.findById(userNo).orElse(null);
            if (user != null) {
                user.setRole(Role.pay); 
                userRepository.save(user);
            }
        }


        return body;
    }
}
