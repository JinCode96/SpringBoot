package kr.co.userprac.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//시큐리티가 제공하는 기본 로그인 화면을 못 쓰고, login 페이지를 커스텀한다.
@Configuration
public class SecurityConfig {
	
	@Autowired
	private SecurityUserService service;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // index는 모두에게 권한
		http.authorizeHttpRequests().antMatchers("/user1/loginSuccess").hasAnyRole("3", "4", "5");
		
		// ------------ 인증 ------------
		
		// 사이트 위변조 요청 방지
		http.csrf().disable(); 
		
		// 시큐리티로 출력할 로그인 설정
		http.formLogin()
		.loginPage("/user1/login")
		.defaultSuccessUrl("/user1/loginSuccess")
		.failureUrl("/user1/login?success=100")
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user1/logout"))
		.logoutSuccessUrl("/user1/login?success=200");
		
		// 사용자 인증 처리 컴포넌트 서비스 등록
		http.userDetailsService(service);

		return http.build();
		
	}
	
	@Bean
    public PasswordEncoder PasswordEncoder () {
        //return new MessageDigestPasswordEncoder("SHA-256");
		return new BCryptPasswordEncoder();
    }
	
}
