package kr.co.ch09.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.ch09.vo.BookVO;
import kr.co.ch09.vo.NaverResultVO;

@Controller
public class BookController {
	@GetMapping("/book/list")
	public String list(String text, Model model) { // 검색어를 매개변수로 받음
		
		// 네이버 검색 API 요청. json 데이터로 출력됨
		String clientId = "EtMqXGHuGR8mCgaMi2l3";
		String ClientSecret = "xi1vPAIaZb";
		
		URI uri = UriComponentsBuilder
				  .fromUriString("https://openapi.naver.com")
				  .path("/v1/search/book.json")
				  .queryParam("query", text) // 검색어
				  .queryParam("display", 10) 
				  .queryParam("start", 1) 
				  .queryParam("sort", "sim") 
				  .encode()
				  .build()
				  .toUri();
		
		RequestEntity<Void> req = RequestEntity
									.get(uri)
									.header("X-Naver-Client-Id", clientId)
									.header("X-Naver-Client-Secret", ClientSecret)
									.build();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
		
		// System.out.println(resp.getBody());
		
		// JSON 파싱
		ObjectMapper om = new ObjectMapper();
		
		NaverResultVO resultVO = null;
		
		try {
			resultVO = om.readValue(resp.getBody(), NaverResultVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// System.out.println(result);
		List<BookVO> books = resultVO.getItems();
		model.addAttribute("books", books);
		
		return "/book/list";
	}
}
