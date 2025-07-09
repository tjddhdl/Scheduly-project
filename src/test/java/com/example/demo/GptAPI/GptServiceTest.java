package com.example.demo.GptAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.gpt.GptService;

@SpringBootTest
public class GptServiceTest {

	@Autowired
	GptService gptService;

	@Test
	void 플랜생성() {
		String answer = gptService.createPlan("자바 기초 지식 공부 10일 플랜 오늘부터 시작하는 걸로 짜줘");
		System.out.println(answer);
	}

	@Test
	void 플랜변환() {
		String apiPlan = "{\r\n"
				+ "\"study\": \"컴활1급 자격증 공부 계획\",\r\n"
				+ "\"list\": [\r\n"
				+ "    {\"date\": \"2022-07-13\", \"content\": \"컴활1급 개요 이해\"},\r\n"
				+ "    {\"date\": \"2022-07-14\", \"content\": \"컴퓨터 원리 및 시스템 기초\"},\r\n"
				+ "    {\"date\": \"2022-07-15\", \"content\": \"운영체제 원리 이해\"},\r\n"
				+ "    {\"date\": \"2022-07-16\", \"content\": \"운영체제 실습\"},\r\n"
				+ "    {\"date\": \"2022-07-18\", \"content\": \"MS-Word 기본\"},\r\n"
				+ "    {\"date\": \"2022-07-19\", \"content\": \"MS-Word 심화 및 실습\"},\r\n"
				+ "    {\"date\": \"2022-07-20\", \"content\": \"MS-Excel 기본\"},\r\n"
				+ "    {\"date\": \"2022-07-21\", \"content\": \"MS-Excel 심화 및 실습\"},\r\n"
				+ "    {\"date\": \"2022-07-22\", \"content\": \"MS-PowerPoint 기본\"},\r\n"
				+ "    {\"date\": \"2022-07-23\", \"content\": \"MS-PowerPoint 심화 및 실습\"},\r\n"
				+ "    {\"date\": \"2022-07-25\", \"content\": \"인터넷 및 이메일 활용 기초\"},\r\n"
				+ "    {\"date\": \"2022-07-26\", \"content\": \"인터넷 및 이메일 활용 심화 및 실습\"},\r\n"
				+ "    {\"date\": \"2022-07-27\", \"content\": \"컴퓨터 그래픽스 기초\" },\r\n"
				+ "    {\"date\": \"2022-07-28\", \"content\": \"컴퓨터 그래픽스 심화 및 실습\" },\r\n"
				+ "    {\"date\": \"2022-07-29\", \"content\": \"데이터베이스 기초\"},\r\n"
				+ "    {\"date\": \"2022-07-30\", \"content\": \"데이터베이스 심화 및 실습\"},\r\n"
				+ "    {\"date\": \"2022-08-01\", \"content\": \"학습한 내용 복습\"},\r\n"
				+ "    {\"date\": \"2022-08-02\", \"content\": \"모의 시험\"},\r\n"
				+ "    {\"date\": \"2022-08-03\", \"content\": \"모의 시험 결과 분석 및 부족한 부분 보완 학습\"},\r\n"
				+ "    {\"date\": \"2022-08-04\", \"content\": \"최종 복습\"},\r\n"
				+ "    {\"date\": \"2022-08-05\", \"content\": \"최종 복습 및 모의 시험\"},\r\n"
				+ "    {\"date\": \"2022-08-06\", \"content\": \"최종 모의 시험 결과 분석 및 부족한 부분 보완 학습\"},\r\n"
				+ "    {\"date\": \"2022-08-08\", \"content\": \"시험 준비 및 마음가짐 다잡기\"},\r\n"
				+ "    {\"date\": \"2022-08-09\", \"content\": \"시험 응시\" }]\r\n"
				+ "}";
		String userOrder = "좀 기니까 13일 플랜으로 줄여주고 7월 13일부터 하루씩 공부하는 걸로 바꿔줘";
		String answer = gptService.convertPlan(apiPlan, userOrder);
		System.out.println(answer);
	}
}
