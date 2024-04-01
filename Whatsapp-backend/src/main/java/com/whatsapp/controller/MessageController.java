package com.whatsapp.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Message;
import com.whatsapp.model.User;
import com.whatsapp.request.SendMessageRequest;
import com.whatsapp.response.ApiResponse;
import com.whatsapp.service.MessageService;
import com.whatsapp.service.UserService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req,
			@RequestHeader("Authorization") String Jwt) throws UserException, ChatException {
		
		User user = userService.findUserProfile(Jwt);
		req.setUserid(user.getId());
		Message message = messageService.sendMessage(req);
		return new ResponseEntity<Message>(message,HttpStatus.OK);
	}
	
	
	@GetMapping("/chat/{chatId}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Message> getChatMessageHandler(@PathVariable ("chatId") Integer chatId, 
			@RequestHeader("Authorization") String Jwt) throws UserException, ChatException {
		
		User user = userService.findUserProfile(Jwt);
		List<Message> messages = messageService.getChatMessages(chatId, user);
		return messages;
	}
	
	
	@DeleteMapping("/{messageId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse deleteMessageHandler(@PathVariable ("messageId") Integer messageId, 
			@RequestHeader("Authorization") String Jwt) throws UserException, ChatException, MessageException {
		
		User user = userService.findUserProfile(Jwt);
		 messageService.deleteMessage(messageId, user);
		 ApiResponse apiresposne = ApiResponse.builder().message("Message deleted successfully").status(false).build();
		return apiresposne;
	}
}
