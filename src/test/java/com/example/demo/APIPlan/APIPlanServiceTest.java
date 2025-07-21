package com.example.demo.APIPlan;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.apiPlan.APIPlan;
import com.example.demo.apiPlan.APIPlanDTO;
import com.example.demo.apiPlan.APIPlanRepository;
import com.example.demo.apiPlan.APIPlanService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootTest
public class APIPlanServiceTest {

	@Autowired
	APIPlanService apiPlanService;
	
	@Autowired
	APIPlanRepository apiPlanRepository;
	
	@Test
	void dtoEntity변환해서추가() throws JsonMappingException, JsonProcessingException {
		String json = """
				{
				  "study": "컴퓨터 일반",
				  "list": [
				    {
				      "date": "2025-07-04",
				      "content": "컴퓨터 구조(하드웨어, CPU)"
				    },
				    {
				      "date": "2025-07-05",
				      "content": "기억장치(램, 롬, 캐시, 보조기억장치)"
				    },
				    {
				      "date": "2025-07-06",
				      "content": "입출력장치(키보드, 프린터, 모니터 등)"
				    }
				  ]
				}
				""";
		APIPlanDTO dto = APIPlanDTO.builder().apiPlanContentList(json).build();
		APIPlan apiPlan = apiPlanService.dtoToEntity(dto);
		apiPlanRepository.save(apiPlan);
	}
	
	@Test
	void entityToDTO변환() throws JsonProcessingException {
		APIPlan apiPlan = apiPlanRepository.findById(2).get();
		APIPlanDTO dto = apiPlanService.EntitytoDTO(apiPlan);
		System.out.println(dto);
	}
	
	@Test
	void remove테스트() {
		apiPlanService.remove(3);
	}
	
	@Test
	void read테스트() throws JsonProcessingException {
		APIPlanDTO dto = apiPlanService.read(2);
		System.out.println(dto);
	}
	
	@Test
	void download테스트() throws JsonProcessingException {
		String json = apiPlanService.download(2);
		System.out.println(json);
	}
	
	@Test
	void 플랜업로드() {
		boolean a = apiPlanService.upload(3);
		System.out.println(a);
	}
	
	@Test
	void 아이디로보드에없는api플랜목록조회() {
		List<APIPlanDTO> list = apiPlanService.readByUserId("dddd");
		System.out.println(list);
	}
}
