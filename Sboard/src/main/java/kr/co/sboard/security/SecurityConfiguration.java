package kr.co.sboard.security;

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
public class SecurityConfiguration {
	
	@Autowired
	private SecurityUserService service;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인가(접근권한) 설정
		http.authorizeHttpRequests().antMatchers("/").permitAll(); // index는 모두에게 권한
		http.authorizeHttpRequests().antMatchers("/list").hasAnyRole("2","3","4","5");  // 2이상 권한을 가져야 list로 접근 가능. ROLE_ 은 자동으로 붙여줌
		http.authorizeHttpRequests().antMatchers("/write", "/view", "/modify").hasAnyRole("3","4","5"); 
		
		// ------------ 인증 ------------
		
		// 사이트 위변조 요청 방지
		http.csrf().disable(); 
		
		// 로그인 설정
		http.formLogin()
		.loginPage("/user/login")
		.defaultSuccessUrl("/list")
		.failureUrl("/user/login?success=100")
		.usernameParameter("uid")
		.passwordParameter("pass");
		
		// 로그아웃 설정
		http.logout()
		.invalidateHttpSession(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/user/login?success=200");
		
		// 사용자 인증 처리 컴포넌트 서비스 등록
		http.userDetailsService(service);
		
		return http.build();
		
	}
	
	@Bean
    public PasswordEncoder PasswordEncoder () {
		return new BCryptPasswordEncoder();
    }
	
}
