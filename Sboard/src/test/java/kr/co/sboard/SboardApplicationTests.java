package kr.co.sboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SboardApplicationTests {
	
	/*
	@Test
	void contextLoads() {
	}
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepo repo;
	
	@Test
	public void insertTest() {
		UserVO vo = UserVO.builder()
					.uid("test1")
					.pass1("1234")
					.name("테스트")
					.nick("테스트1")
					.email("test1@naver.com")
					.hp("010-1111-8888")
					.regip("0:0:0:0:0:0:0:1")
					.build();
		
		int result = service.insertUser(vo);
		
		log.info("result : " + result);
	}
	
	@Test
	public void countUid() {
		int result = repo.countByUid("test");
		log.info("result : " + result);
	}
	*/

}
