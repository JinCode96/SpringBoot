package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.ch08.repository.User2Repo;
import kr.co.ch08.vo.User2VO;

@Service
public class SecurityUserService implements UserDetailsService {
	
	// 이걸로 사용자 조회
	@Autowired
	private User2Repo repo;
	
	// 인증관리 필터에서 호출됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 스프링 시큐리티 인증 동작방식은 아이디/패스워드를 한번에 조회하는 방식이 아닌
		// 아이디만 이용해서 사용자 정보를 로딩하고 나중에 패스워드를 검증하는 방식
		User2VO user = repo.findById(username).get();
		
		if(user == null) {
			// 계정이 없을 때
			throw new UsernameNotFoundException(username);
		}
		
		// Security가 제공하는 기본 사용자 객체 생성방법
		/*
		// 인증을 거친다. User에서 인증처리을 함. userDts 객체를 세션으로 등록한다. 빌더 패턴
		UserDetails userDts = User.builder()
							  .username(user.getUid())
							  .password(user.getPass())
							  .roles("MEMBER") // member로 하면 admin은 접근 안됨
							  .build();
		*/
		
		/* setter 방식
		MyUserDetails myUser = new MyUserDetails();
		myUser.setUid(user.getUid());
		myUser.setPass(user.getPass());
		myUser.setName(user.getName());
		myUser.setHp(user.getHp());
		myUser.setAge(user.getAge());
		myUser.setRdate(user.getRdate());
		*/
		
		UserDetails myUser = MyUserDetails.builder()
								.uid(user.getUid())
								.pass(user.getPass())
								.name(user.getName())
								.grade(user.getGrade())
								.hp(user.getHp())
								.age(user.getAge())
								.rdate(user.getRdate())
								.build();
		
		return myUser;
	}
}
