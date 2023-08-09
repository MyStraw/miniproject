package edu.pnu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.pnu.config.auth.JWTAuthorizationFilter;
import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.persistence.MemberRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private AuthenticationConfiguration authConfig;

	@Autowired
	private MemberRepository memberRepo;
	
	@SuppressWarnings("removal")
	@Autowired
	
//	@Qualifier("corsConfigurationSource") //이거 없으면 @Bean에 2개가 올라가서 뭘 골라야 할지 모른다. WebConfig에 있는걸 고르게 하자.
//	private CorsConfigurationSource corsConfigurationSource
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable());
		///http.cors(cors -> cors.disable());	
		http.cors();
		
//		  http.cors(cors -> {
//		        cors.configurationSource(corsConfigurationSource);
//		    });
		
		

		
		
		http.authorizeHttpRequests(security -> { //여기서 권한별 설정
			security.requestMatchers("/board/create/**").authenticated()
					.requestMatchers("/board/update/**").hasAnyRole("MANAGER", "ADMIN")
					.requestMatchers("/board/delete/**").hasRole("ADMIN") //자동으로 ROLE_ 이걸 넣어서 읽는다. 그래서 테이블엔 ROLE_ADMIN으로 넣어야.
					.anyRequest().permitAll();
		});

		http.formLogin(frmLogin -> frmLogin.disable());
		http.sessionManagement(ssmg -> ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager(),memberRepo));
		http.addFilter(new JWTAuthorizationFilter(authConfig.getAuthenticationManager(), memberRepo));

		return http.build();
	}
}
