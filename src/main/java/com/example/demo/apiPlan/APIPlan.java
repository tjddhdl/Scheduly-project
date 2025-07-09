package com.example.demo.apiPlan;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_api_plan")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class APIPlan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int apiPlanNo;
	
	@Column(columnDefinition = "json")
	@Convert(converter = JsonConverter.class)
	APIPlanContentList apiPlanContentList;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class APIPlanContentList{
		private String study;
		private List<StudyItem>list;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class StudyItem {
		private String date;
		private String content;
	}
}
