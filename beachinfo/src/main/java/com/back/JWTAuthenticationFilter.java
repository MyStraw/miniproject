package com.back;
//package edu.pnu.config.filter;
//
//import java.io.IOException;
//import java.util.Date;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import edu.pnu.domain.Member;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
////로그인 인증을 위한 시큐리티 필터
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//	// 시큐리티에서 제공하는 인증 매니저 인터페이스
//	private final AuthenticationManager authenticationManager;
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
//			throws AuthenticationException {
//		// req의 Body에 JSON으로 담겨오는 id/password를 읽어서 Member 객체로 받아온다
//		ObjectMapper om = new ObjectMapper();
//		try {
//			Member member = om.readValue(req.getInputStream(), Member.class);
//			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
//			// authToken을 사용하여 사용자를 인증하고 인증 성공시 Authentication 객체 반환
//			Authentication auth = authenticationManager.authenticate(authToken);
//			log.info("attemptAuthentication :[" + member.getUsername() + "]");
//			return auth;
//
//		} catch (Exception e) {
//			log.info("Not Authenticated : " + e.getMessage());
//		}
//		return null;
//
//	}
//
//	// 인증이 성공 했을 때
//	// 필터와 관련된 클래스에서만 오버라이드하여 사용하기 위해 protected 사용
//	@Override
//	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain,
//			Authentication authResult) throws IOException, ServletException {
//		
//		User user = (User)authResult.getPrincipal();
//		log.info("successful : " + user.toString());
//		
//		// JWT 생성
//		String jwtToken = JWT.create()
//				.withClaim("id", user.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10))
//				.sign(Algorithm.HMAC256("edu.pnu.jwtkey"));
//		// Bearer : JWT 임을 나타내는 용어
//		// Postman get > http://localhost:8080/member > Header > key: Authorization, Value: Bearer eyJhbGciOiJIU~ 입력
//		resp.addHeader("Authorization","Bearer" + jwtToken);
//		chain.doFilter(req, resp);
//	}}
//
//
//
//
