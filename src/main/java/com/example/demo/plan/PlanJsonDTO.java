package com.example.demo.plan;

import java.util.List;

import lombok.Data;

@Data
public class PlanJsonDTO {
	private String study;
	private List<StudyItem> list;
	
	@Data
	public static class StudyItem {
		private String date;
		private String content;
	}
}
