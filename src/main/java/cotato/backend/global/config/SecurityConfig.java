package cotato.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SecurityConfig {

	private final CorsFilter corsFilter;
	private static final String[] WHITE_LIST = {
		"/",
		"/error",
		"/favicon.ico",
		"/v3/api-docs/**",
		"/swagger-ui/**",
		"/swagger-resources/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
		AuthenticationManager authenticationManager = sharedObject.build();
		http.authenticationManager(authenticationManager);

		http
			// CSRF 보호 비활성화
			.csrf(AbstractHttpConfigurer::disable)
			// 기본 HTTP 인증 비활성화
			.httpBasic(AbstractHttpConfigurer::disable)
			// 폼 로그인 비활성화
			.formLogin(AbstractHttpConfigurer::disable)
			// 로그아웃 기능 비활성화
			.logout(AbstractHttpConfigurer::disable)
			// X-Frame-Options 헤더 설정 비활성화
			.headers(c -> c.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::disable).disable())
			// CORS 필터 추가
			.addFilter(corsFilter)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(WHITE_LIST).permitAll()
				.anyRequest().authenticated()
			)
		;

		return http.build();
	}
}
