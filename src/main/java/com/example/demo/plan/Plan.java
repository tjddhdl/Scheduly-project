package com.example.demo.plan;

import com.example.demo.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tbl_plan")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int planNo;
	
	@ManyToOne
	@JoinColumn(name = "user")
	User user;
	
	@Column
	String planName;
	
	@Enumerated(EnumType.STRING)
	@Column
	Status status;
}
