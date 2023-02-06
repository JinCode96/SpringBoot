package kr.co.ch08.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 시큐리티가 제공하는 기본 로그인 화면을 못 쓰고, login 페이지를 커스텀한다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // index화면일 때
		http.authorizeHttpRequests().antMatchers("/admin/**").hasRole("ADMIN"); // /user2/login 창으로 간다.
		http.authorizeHttpRequests().antMatchers("/member/**").hasAnyRole("ADMIN", "MEMBER"); // /user2/login 창으로 간다.
		http.authorizeHttpRequests().antMatchers("/user2/loginSuccess").hasAnyRole("3", "4", "5"); // grade 3권한 이상의 사용자가 접근 할 수 있음
		// guest는 생략 권한 줄 게 없음
		
		// 인증 -----------------
		// 사이트 위변조 요청 방지
		http.csrf().disable();
		
		// 시큐리티로 출력할 로그인 설정
		http.formLogin()
		.loginPage("/user2/login")
		.defaultSuccessUrl("/user2/loginSuccess")
		.failureUrl("/user2/login?success=100")
		.usernameParameter("uid")
		.passwordParameter("pass");
				
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user2/logout"))
		.logoutSuccessUrl("/user2/login?success=200");
	}
	
	@Autowired
	private SecurityUserService service;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// 로그인 인증처리 서비스 등록. 
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
}
