package edu.pnu.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.entity.Busanuser;
import edu.pnu.repository.BusanUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

	private final AuthenticationManager authManager;
	private final BusanUserRepository userRepository;
	private final BCryptPasswordEncoder encoder;

	@Value("${jwt.secretKey}")
	private String secretKey;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Busanuser user) {
		if (user.getUsername() == null || user.getPassword() == null) {
			return ResponseEntity.badRequest().body("Please Enter the Username or Password.");
		}

		try {
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user.getUsername(),
					user.getPassword());
			Authentication auth = authManager.authenticate(upat);

			String token = JWT.create().withClaim("username", user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
					.sign(Algorithm.HMAC256(secretKey));

			System.out.println("token: " + token);

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong Username or Password");
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody Busanuser user) {
		String username = user.getUsername();

		// Username 글자수 및 패턴 검사
		if (username.length() < 3 || username.length() > 15) {
			return ResponseEntity.status(400).body("Username length must be between 3 and 15 characters");
		}
		if (!username.matches("^[a-zA-Z0-9]*$")) {
			return ResponseEntity.status(400).body("Username can only contain English letters and numbers");
		}

		if (userRepository.existsById(username)) {
			return ResponseEntity.status(409).body("ID already exists");
		}

		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		// 기본 역할 설정
		user.setRole("ROLE_MEMBER");
		user.setEnabled(true);

		userRepository.save(user);
		return ResponseEntity.status(201).body("Signup successful");
	}

}
