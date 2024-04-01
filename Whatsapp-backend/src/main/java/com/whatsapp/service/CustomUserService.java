package com.whatsapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whatsapp.model.User;
import com.whatsapp.repository.UserRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {

	private final UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username)
				.orElseThrow(()-> new UsernameNotFoundException("User doesnot exist with email :" + username));
		
		List<GrantedAuthority> list = new ArrayList<>();
		UserDetails userDetails =new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), list);
		 return userDetails;
	}

}
