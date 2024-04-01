package com.whatsapp.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.User;
import com.whatsapp.request.UpdateUserRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) 
			throws UserException{
		
		User user= userService.findUserProfile(token);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);	
	}
	
	@GetMapping("/{query}")
	public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query){
		
		List<User> Users = userService.searchUser(query);
		return new ResponseEntity<List<User>>(Users,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public ApiResponse updateUseHandler(@RequestBody UpdateUserRequest updateUserRequest,
			@RequestHeader("Authorization") String token) throws UserException{
		
		User user= userService.findUserProfile(token);
		 userService.updateUser(user.getId(), updateUserRequest);
		ApiResponse apiresponse = ApiResponse.builder().message("user updated successfully").status(true).build();
		return apiresponse;
		
	}
}
