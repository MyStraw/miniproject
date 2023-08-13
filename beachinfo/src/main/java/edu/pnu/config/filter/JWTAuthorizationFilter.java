package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter{
	// 사용자 정보 읽기
	private final MemberRepository memRepo;
	
	
	private final JwtService jwtService;
	
	
	// 실제 필터링 작업 수행
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) 
			throws IOException, ServletException {
		// 요청정보에서 jwt토큰 읽어오기
		String jwtToken = req.getHeader(HttpHeaders.AUTHORIZATION);
		if(jwtToken == null) {
			chain.doFilter(req, resp);
			return;
		}
		
		
		// 토큰에서 사용자 이름 읽어오기 
		String srcToken = jwtToken.replace("Bearer ", "");		
		String username = jwtService.getUsernameFromToken(srcToken);		
		
		
		// 읽어온 사용자 이름으로 db에서 사용자 정보 읽어오기 
		Optional<Member> opt = memRepo.findByUsername(username);
		if(!opt.isPresent()) {
			chain.doFilter(req, resp);
			return;
		}
		
		Member findmember = opt.get();
		User user =  new User(findmember.getUsername(), findmember.getPassword(), findmember.getAuthorities());
		// Authentication 객체 생성, 암호는 필요없음
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(req, resp);
	}
}
