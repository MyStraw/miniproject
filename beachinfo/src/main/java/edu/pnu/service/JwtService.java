package edu.pnu.service;

import java.sql.Date;

//import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;

@Service
public class JwtService {

	static final long EXPIRATIONTIME = 1000*60*10;
	
	static final String PREFIX = "Bearer ";
	
	static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	
	public String getToken(String username, String role) {
		String token = Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATIONTIME))
				.signWith(key)
				.compact();
		
		return token;
	}
	
	public String getUsernameFromToken(String token) {
		
//		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (token != null) {
			String user = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (user != null) 
				return user;
		}
		
		return null;
	}
	
	
	public String getAuthUser(HttpServletRequest req) {
		String token = req.getHeader("Refresh-Token");
//		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (token != null) {
			String user = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (user != null) 
				return user;
		}
		
		return null;
	}
}
