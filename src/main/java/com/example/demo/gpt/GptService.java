package com.example.demo.gpt;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GptService {

	@Value("${apikey}")
	String key;

	// 요구사항 가지고 플랜 만들어주는 기능
	public String createPlan(String userOrder) {

		String apiKey = key;

		HttpClient client = HttpClient.newHttpClient();

		// 메시지 구성
		JSONArray messages = new JSONArray();
		JSONObject message = new JSONObject();
		message.put("role", "system");
		message.put("content",
				"요구사항에 맞춰 { \"study\": \"주제명\", \"list\": [ { \"date\": \"YYYY-MM-DD\", \"content\": \"내용\"}]}의 양식으로 한국어로 공부 계획을 만들어. 날짜는 하루씩 올라가게 만들고 끝나면 요구사항 다시 확인하면서 틀린 내용 수정해. 반드시 JSON데이터만 출력해");
		messages.put(message);

		JSONObject message2 = new JSONObject();
		message2.put("role", "user");
		message2.put("content", userOrder);
		messages.put(message2);

		// 요청 본문
		JSONObject json = new JSONObject();
		json.put("model", "gpt-4o");
		json.put("messages", messages);
		json.put("stream", false);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.openai.com/v1/chat/completions"))
				.header("Authorization", "Bearer " + apiKey).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json.toString())).build();

		// 응답 처리
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		JSONObject responseBody = new JSONObject(response.body());

		JSONArray choices = responseBody.getJSONArray("choices");
		JSONObject choice = choices.getJSONObject(0);

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// JSON 문자열을 클래스로 변환
		ChatResponse gptResponse = null;
		try {
			gptResponse = mapper.readValue(choice.toString(2), ChatResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// 답변만 꺼내기
		ChatResponse.Message responseMessage = gptResponse.getMessage();

		String content = responseMessage.getContent();
		if (content.startsWith("```json")) {
			content = content.substring(7);
		}
		if (content.endsWith("```")) {
			content = content.substring(0, content.length() - 3);
		}
		content = content.trim();
		// GPT 답변 반환
		return content;
	}

	// apiPlan의 데이터를 자신에 맞게 가공하기
	public String convertPlan(String apiPlanJson, String userOrder) {

		String apiKey = key;

		HttpClient client = HttpClient.newHttpClient();

		// 메시지 구성
		JSONArray messages = new JSONArray();
		JSONObject message = new JSONObject();
		message.put("role", "system");
		message.put("content",
				"주어진 자료를 요구사항에 맞춰 { \"study\": \"주제명\", \"list\": [ { \"date\": \"YYYY-MM-DD\", \"content\": \"내용\"}]}의 양식으로 한국어로 정보를 수정해. 날짜는 하루씩 올라가게 만들어. 만들면 요구사항 다시 확인하면서 틀린 내용 수정해. 반드시 JSON데이터로만 출력해");
		messages.put(message);

		JSONObject message2 = new JSONObject();
		message2.put("role", "user");
		message2.put("content", apiPlanJson);
		messages.put(message2);

		JSONObject message3 = new JSONObject();
		message3.put("role", "user");
		message3.put("content", userOrder);
		messages.put(message3);

		// 요청 본문
		JSONObject json = new JSONObject();
		json.put("model", "gpt-4o");
		json.put("messages", messages);
		json.put("stream", false);

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.openai.com/v1/chat/completions"))
				.header("Authorization", "Bearer " + apiKey).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json.toString())).build();

		// 응답 처리
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		JSONObject responseBody = new JSONObject(response.body());

		JSONArray choices = responseBody.getJSONArray("choices");
		JSONObject choice = choices.getJSONObject(0);

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// JSON 문자열을 클래스로 변환
		ChatResponse gptResponse = null;
		try {
			gptResponse = mapper.readValue(choice.toString(2), ChatResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// 답변만 꺼내기
		ChatResponse.Message responseMessage = gptResponse.getMessage();

		String content = responseMessage.getContent();
		if (content.startsWith("```json")) {
			content = content.substring(7);
		}
		if (content.endsWith("```")) {
			content = content.substring(0, content.length() - 3);
		}
		content = content.trim();
		
		// GPT 답변 반환
		return content;
	}
}
