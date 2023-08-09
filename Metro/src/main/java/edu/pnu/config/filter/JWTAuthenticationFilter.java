package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final MemberRepository memRepo;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ObjectMapper om = new ObjectMapper();
		try {

			Member member = om.readValue(request.getInputStream(), Member.class);
			// ------ 유저가 틀렸을때. 추가코드---------
			Optional<Member> option = memRepo.findById(member.getUsername());
			if (!option.isPresent()) {

				log.info("Not Authenticated : Not found user!");
				return null;
			}

			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(),
					member.getPassword()); 
			Authentication auth = authenticationManager.authenticate(authToken);
			log.info("Authenticated:[" + member.getUsername() + "]");
			return auth;
		} catch (Exception e) {
			log.info("Not Authenticated : Not Match password!");
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		log.info("successfulAuthentication:" + user.toString());

		String jwtToken = JWT.create()
				.withClaim("username", user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.sign(Algorithm.HMAC256("edu.pnu.jwtkey"));
		
	

		response.addHeader("Authorization", "Bearer " + jwtToken);
		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"message\":\"로그인 성공\"}");
		// ,\"token\":\"" + jwtToken + "\"
		response.getWriter().flush(); // 출력 스트림 플러시
		response.getWriter().close(); // 출력 스트림 종료
//		chain.doFilter(request, response);
	

	}
}
