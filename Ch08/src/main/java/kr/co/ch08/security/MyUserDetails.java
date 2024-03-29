package kr.co.ch08.security;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyUserDetails implements UserDetails { // 로그인 사용자 객체

	private String uid;
	private String pass;
	private String name;
	private int grade;
	private String hp;
	private int age;
	private LocalDateTime rdate;
	
	public void roles(String... grade) {} // 가변 매개변수
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 계정이 갖는 권한 목록
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + grade));
		
		return authorities;
	}

	@Override
	public String getPassword() {
		// 계정이 갖는 비밀번호
		return pass;
	}

	@Override
	public String getUsername() {
		// 계정이 갖는 아이디
		return uid;
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정 만료 여부(true:만료안됨, false:만료. 로그인 안됨)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정 잠김 여부(true:잠김안됨, false:잠김)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 만료여부(true:만료 안됨, false:만료)
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 계정 활성화 여부(true:활성화, false:비활성화)
		return true;
	}

}
