package com.whatsapp.config;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.whatsapp.jwt.JwtTokenValidator;

import jakarta.servlet.http.HttpServletRequest;



@Configuration
public class SecurityConfig {

	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf-> csrf.disable());
		http .authorizeHttpRequests((requests) -> requests.requestMatchers("/api/**").authenticated() 
						.anyRequest().permitAll()); 																
		http.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);	
		http.cors( (cors)-> cors.configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				
				CorsConfiguration cfg= new CorsConfiguration();
				cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				return cfg;
			}
			
		}));
		
		http.httpBasic();
		http.formLogin();	
		return http.build(); 
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
