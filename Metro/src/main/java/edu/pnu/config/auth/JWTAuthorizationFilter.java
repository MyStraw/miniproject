package edu.pnu.config.auth;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private MemberRepository memRepo;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memRepo) {
		super(authenticationManager);
		this.memRepo = memRepo;
	}

	@Override //모든 리퀘스트가 이 필터를 통과. 여기서 JWT토큰의 유효성을 검사.
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String srcToken = request.getHeader("Authorization"); //헤더에 Au~라는 키값을 가진 헤더를 갖고온다. 
		if (srcToken == null || !srcToken.startsWith("Bearer ")) { //없다면 다음 필터로 넘어간다.
			chain.doFilter(request, response);
			return;
		}

		//누군지 해석. 토큰에서 유저의 정보를 추출한다. Bearer 문자 제거하고 순수한 JWT 토큰만 뽑는거.
		String jwtToken = srcToken.replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwtkey")).build().verify(jwtToken).getClaim("username")
				.asString(); //username을 갖고온다.
		Optional<Member> opt = memRepo.findById(username); //위에서 추출한 username을 이용해 DB에서 회원을 찾아본다. 일치하는지.
		if (!opt.isPresent()) { //만약에 해당회원 존재안해? 그럼 다음 필터로 넘어가.
			chain.doFilter(request, response);
			return;
		}

		Member findmember = opt.get(); //DB에서 찾은 정보로 User라는 객체를 생성한다. username, password, 권한이 들어있다.
		User user = new User(findmember.getUsername(), findmember.getPassword(), findmember.getAuthorities());
		//User를 이용해서 Authentication 객체 생성, 암호는 필요없음. 이걸 SecurityContextHolder에 설정. 이를통해 시스템은 이 사용자가 인증된것으로 간주.
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response); //현재 필터의 처리가 끝나고 다음 필터로 request,와 response를 넘김.
	}
}
