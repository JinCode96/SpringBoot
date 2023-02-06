package kr.co.userprac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.userprac.repository.User1Repo;
import kr.co.userprac.vo.User1VO;

@Service
public class User1Service {
	
	@Autowired
	private User1Repo repo;
	
	public User1VO selectUser1(String uid, String pass) {
		return repo.findUser1VOByUidAndPass(uid, pass);
	}
	
	
}
