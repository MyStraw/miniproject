package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Member;
import edu.pnu.service.JwtService;
import edu.pnu.service.UserDetailsServiceImpl;
@RestController
//@RequiredArgsConstructor
public class LoginController {
	
//	private final AuthenticationManager authenticationManager;
//
//	@PostMapping("/login")
//	public ResponseEntity<?> loginPost(@RequestBody Member member ) {
//		
//		System.out.println("member:" + member);
//		
//		Authentication authToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());
//		// authToken을 사용하여 사용자를 인증하고 인증 성공시 Authentication 객체 반환
//		Authentication auth = authenticationManager.authenticate(authToken);
//		
//		User user = (User)auth.getPrincipal();
//		
//		// JWT 생성
//		String jwtToken = JWT.create()
//				.withClaim("username", user.getUsername())
//				.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*10))
//				.sign(Algorithm.HMAC256("edu.pnu.jwtkey"));
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization","Bearer" + jwtToken);
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		return new ResponseEntity<>("OK", headers, HttpStatus.OK);
//		
//	}
//
//	@GetMapping("/test")
//	public String Test() {
//		return "test";
//	}
	
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@PostMapping("/login") //여기서 로그인 실패 걸러주는게 없는건가.
	public ResponseEntity<?> getToken(@RequestBody Member member) {
		
		System.out.println("member : " + member);
		UsernamePasswordAuthenticationToken creds =
				new UsernamePasswordAuthenticationToken(
						member.getUsername(), 
						member.getPassword());	

		Authentication auth = authenticationManager.authenticate(creds);
		UserDetails userDetails = (UserDetails) auth.getPrincipal();

	    // Extract role from userDetails
	    String role = userDetails.getAuthorities().iterator().next().getAuthority();
		// Generate token
		String jwts = jwtService.getToken(auth.getName(), role);

		// Build response with the generated token
		return ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization") //여기서 클라이언드에 response 할때. Authorization 헤더 포함해서 보냄. 즉, 헤더를 클라이언트에 노출.
				.build();	//토큰의 만료시간이 어디에 있는지? -> JwtService에 getToken에 있다.

	}
	
	
	@PostMapping("/join")
    public String join(@RequestBody Member member) {
        // 회원 정보를 받아서 데이터베이스에 저장하고, 홈 페이지 등으로 리다이렉트합니다.
        userDetailsService.saveMember(member);
        return "redirect:/";
    }
}
