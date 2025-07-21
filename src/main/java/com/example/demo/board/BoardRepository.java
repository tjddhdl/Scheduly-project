package com.example.demo.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.apiPlan.APIPlan;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	Page<Board> findAll(Pageable pageable);
	
	Board findByApiPlan(APIPlan apiPlan);
}
