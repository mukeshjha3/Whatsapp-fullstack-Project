package com.whatsapp.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.Message;
import com.whatsapp.model.User;
import com.whatsapp.repository.MessageRepo;
import com.whatsapp.request.SendMessageRequest;

@Service
public class MessageServiceImpl implements MessageService {

	private UserService userService;
	private ChatService chatService;
	private MessageRepo messageRepo;

	public MessageServiceImpl(UserService userService, ChatService chatService, MessageRepo messageRepo) {
		super();
		this.userService = userService;
		this.chatService = chatService;
		this.messageRepo = messageRepo;
	}

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
		User user = userService.findUserByUserId(req.getUserid());
		Chat chat = chatService.findChatByChatId(req.getChatId());
		
		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimestamp(LocalDateTime.now());
		
		return message;
	}

	@Override
	public List<Message> getChatMessages(Integer chatId, User reqUser) throws ChatException, UserException {
		Chat chat= chatService.findChatByChatId(chatId);
		if(!chat.getUsers().contains(reqUser)) {
			throw new UserException("You are not releted to this chat " + chat.getId());
		}
		List<Message> messages = messageRepo.findByChatId(chat.getId());
		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {
		Message message = messageRepo.findById(messageId)
				.orElseThrow(()-> new MessageException("message not found with messageId :" +messageId ));
		return message;
	}

	@Override
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException, UserException {
		Message message = messageRepo.findById(messageId)
				.orElseThrow(()-> new MessageException("message not found with messageId :" +messageId ));
		
		if (message.getUser().getId().equals(reqUser.getId())) {
			messageRepo.deleteById(messageId);
		}
		else {
			throw new UserException("you can't delete another user's message " +reqUser.getFull_name());
		}

	}

}
