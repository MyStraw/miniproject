package edu.pnu.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.JwtService;
import edu.pnu.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtService jwtService;

	// 무한루프 수정
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	MemberRepository memberRepo;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(abcd -> abcd.disable());
		http.cors(cors -> {
			cors.configurationSource(corsConfigurationSource());
		});

		http.authorizeHttpRequests(security -> {
			security.requestMatchers("/member/**").authenticated().requestMatchers("/manager/**")
					.hasAnyRole("MANAGER", "ADMIN").requestMatchers("/admin/**").hasRole("ADMIN").anyRequest()
					.permitAll();
		});

		http.formLogin(frmLogin -> frmLogin.disable());
		http.sessionManagement(abcd -> abcd.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo, jwtService), // autowired할 수 있는 객체가 아님
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true); // false라도 괜찮음. 어차피 JWT는 토큰을 이용하는거라. 이건 쿠키.
		// config.applyPermitDefaultValues(); 기본설정. 세부설정을 위에 3줄 해줬으니 이건 필요없다.

		config.addExposedHeader("Authorization"); //cors 정책설정용. 클라이언트가 헤더에 접근하는걸 허용.

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}

}