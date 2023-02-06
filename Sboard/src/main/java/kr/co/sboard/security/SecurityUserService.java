package kr.co.sboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.sboard.entity.UserEntity;
import kr.co.sboard.repository.UserRepo;

@Service
public class SecurityUserService implements UserDetailsService {
	
	// 이걸로 사용자 조회
	@Autowired
	private UserRepo repo;
	
	// 인증관리 필터에서 호출됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 스프링 시큐리티 인증 동작방식은 아이디/패스워드를 한번에 조회하는 방식이 아닌
		// 아이디만 이용해서 사용자 정보를 로딩하고 나중에 패스워드를 검증하는 방식
		UserEntity user = repo.findById(username).get();
		
		if(user == null) {
			// 계정이 없을 때
			throw new UsernameNotFoundException(username);
		}
		
		// 기본 사용자 객체 생성방법
		// 인증을 거친다. User에서 인증처리을 함. myUser 객체를 세션으로 등록한다. 빌더 패턴
		UserDetails myUser = MyUserDetails.builder()
										  .user(user)
										  .build();
		
		
		return myUser;
	}
}
