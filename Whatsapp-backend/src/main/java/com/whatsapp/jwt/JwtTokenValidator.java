package com.whatsapp.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);

		if (jwt != null) {
			try {
				
				jwt = jwt.substring(7);
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_kEY.getBytes(StandardCharsets.UTF_8));
				
				Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
				
				String email = String.valueOf(claims.get("email"));
				String authority = String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
				
				Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
				System.out.println("Validation Completed");

			} catch (Exception e) {
				throw new BadCredentialsException("Invalid token received......");
			}
		}

		filterChain.doFilter(request, response);
	}

}
