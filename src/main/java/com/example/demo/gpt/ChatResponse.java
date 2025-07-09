package com.example.demo.gpt;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
	private String finish_reason;
	private int index;
	private Message message; // 답변
	private Object logprobs;

	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Message {
		private String role;
		private String content;
		private Object refusal;
		private List<Object> annotations;
	}
}
