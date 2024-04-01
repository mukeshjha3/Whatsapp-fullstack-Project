package com.whatsapp.controller;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.whatsapp.exception.UserException;
import com.whatsapp.jwt.TokenProvider;
import com.whatsapp.model.User;
import com.whatsapp.repository.UserRepo;
import com.whatsapp.request.LoginRequest;
import com.whatsapp.response.AuthResponse;
import com.whatsapp.service.CustomUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final CustomUserService customUserService;
	
	public AuthController(UserRepo userRepo,PasswordEncoder passwordEncoder,
			TokenProvider tokenProvider,CustomUserService customUserService) {
		
		super();
		this.userRepo = userRepo;
		this.passwordEncoder=passwordEncoder;
		this.tokenProvider=tokenProvider;
		this.customUserService=customUserService;
	}


	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("/signup")
	public AuthResponse createUserHandler(@RequestBody User user) throws UserException {
		
		String email = user.getEmail();
	    String password = user.getPassword();
	    Optional<User> existedUser = userRepo.findByEmail(email);
	    if(existedUser.get()!= null) {
		   throw new UserException("User Already exist with email : " + email);
	   }
	    user.setPassword(passwordEncoder.encode(password));
	    userRepo.save(user);
	    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email,password);
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    String JwtToken = tokenProvider.generateToken(auth);
	    AuthResponse authResponse = AuthResponse.builder().Jwt(JwtToken).isAuth(true).build();
	    
		return authResponse;
		
	}
	
	@PostMapping("/signin")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public AuthResponse loginHandler(@RequestBody LoginRequest loginRequest) throws UserException {
	String email = loginRequest.getEmail();
	String password = loginRequest.getPassword();
	Authentication authentication = authenticate(email, password);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	String JwtToken = tokenProvider.generateToken(authentication);
	AuthResponse authResponse = AuthResponse.builder().Jwt(JwtToken).isAuth(true).build();
	return authResponse;
	}
	
	public Authentication authenticate(String username ,String rawpassword) {
		UserDetails userdetails = customUserService.loadUserByUsername(username);
		if (userdetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}
		String encryptedPassword = userdetails.getPassword();
		boolean result = passwordEncoder.matches(rawpassword, encryptedPassword);
		if (result == false) {
			throw new BadCredentialsException("Invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
	}
}
