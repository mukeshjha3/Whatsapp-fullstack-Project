package com.whatsapp.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.User;
import com.whatsapp.request.GroupChatRequest;
import com.whatsapp.request.SingleChatRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.service.ChatService;
import com.whatsapp.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

	private final ChatService chatService;
	private final UserService userService;

	@PostMapping("/single")
	public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
			@RequestHeader("Authorization") String jwt) throws UserException {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
		return new ResponseEntity<>(chat, HttpStatus.OK);

	}

	@PostMapping("/group")
	public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,
			@RequestHeader("Authorization") String jwt) throws UserException {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.createGroup(groupChatRequest, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);

	}

	@GetMapping("/{chatId}")
	@ResponseStatus(code = HttpStatus.OK)
	public Chat findChatByIdHandler(@PathVariable("chatId") Integer chatId, @RequestHeader("Authorization") String jwt)
			throws UserException, ChatException {
		Chat chat = chatService.findChatByChatId(chatId);
		return chat;

	}

	@GetMapping("/user")
	public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt)
			throws UserException {

		User reqUser = userService.findUserProfile(jwt);
		List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
		return new ResponseEntity<>(chats, HttpStatus.OK);

	}
	
	@PutMapping("/{chatId}/add/{userId}")
	public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable("chatId") Integer chatId,
			@PathVariable("userId") Integer userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {

		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.addUserToGroup(userId, chatId, reqUser);
		return new ResponseEntity<Chat>(chat, HttpStatus.OK);

	}
	
	@PutMapping("/{chatId}/remove/{userId}")
	public ResponseEntity<Chat> removeUserToGroupHandler(@PathVariable("chatId") Integer chatId,
			@PathVariable("userId") Integer userId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		
		User reqUser = userService.findUserProfile(jwt);
		Chat chat = chatService.removeFromGroup(userId, chatId, reqUser);
		return new ResponseEntity<>(chat, HttpStatus.OK);
		
	}
	@DeleteMapping("/delete/{chatId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse deletChatHandler(@PathVariable("chatId") Integer chatId,
			@RequestHeader("Authorization") String jwt) throws UserException, ChatException {
		
		User reqUser = userService.findUserProfile(jwt);
		chatService.deleteChat(chatId, reqUser.getId());
		ApiResponse apiResponse = ApiResponse.builder().message("chat is deleted successfully").status(true).build();
		return apiResponse;
		
	}

}