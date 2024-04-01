package com.whatsapp.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenProvider {

	private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_kEY.getBytes(StandardCharsets.UTF_8));

	public String generateToken(Authentication authentication) {

		String jwt = Jwts.builder()
				.issuer("Mukesh")
				.subject("JWT Token")
				.claim("email", authentication.getName())
				.issuedAt(new Date()).expiration(new Date((new Date()).getTime() + 86400000))
				.signWith(key)
				.compact();
		return jwt;
	}

	public String getEmailFromToken(String token) {
		token = token.substring(7);
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		String email = (String) claims.get("email");
		return email;

	}
}
