package com.whatsapp.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.User;
import com.whatsapp.repository.ChatRepo;
import com.whatsapp.request.GroupChatRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRepo chatRepo;
	private final UserService userService;

	@Override
	public Chat createChat(User reqUser, Integer userId2) throws UserException {
		User user = userService.findUserByUserId(userId2);
		Chat isChatExist = chatRepo.findSingleChatByUserId(user, reqUser);

		if (isChatExist != null) {
			return isChatExist;
		}
		Chat chat = new Chat();
		chat.setCreatedby(reqUser);
		chat.getUsers().add(user);
		chat.getUsers().add(reqUser);
		chat.setGroup(false);
		return chat;
	}

	@Override
	public Chat findChatByChatId(Integer ChatId) throws ChatException {
		Chat chat = chatRepo.findById(ChatId)
				.orElseThrow(() -> new ChatException("chat not found with chatId :" + ChatId));
		return chat;
	}

	@Override
	public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
		User user = userService.findUserByUserId(userId);
		List<Chat> chats = chatRepo.findchatByUserId(user.getId());
		return chats;
	}

	@Override
	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException {
		Chat group = new Chat();
		group.setGroup(true);
		group.setChat_image(req.getChat_image());
		group.setChat_name(req.getChat_name());
		group.setCreatedby(reqUser);
		group.getAdmins().add(reqUser);
		for (Integer userId : req.getUserid()) {
			User user = userService.findUserByUserId(userId);
			group.getUsers().add(user);
		}

		return group;
	}

	@Override
	public Chat addUserToGroup(Integer userId, Integer chatId, User requser) throws UserException, ChatException {

		Chat chat = chatRepo.findById(chatId).orElseThrow(() -> new ChatException("chat not found with id :" + chatId));
		User user = userService.findUserByUserId(userId);

		if (chat.getAdmins().contains(requser)) {
			chat.getUsers().add(user);
			chatRepo.save(chat);
		} else {
			throw new UserException("Sorry you are not an admin to add user..");
		}
		return chat;
	}

	@Override
	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException {
		Chat chat = chatRepo.findById(reqUser.getId())
				.orElseThrow(() -> new ChatException("Chat not found With chatId :" + chatId));
		if (chat.getUsers().contains(reqUser)) {
			chat.setChat_name(groupName);
			chatRepo.save(chat);
		} else {
			throw new UserException("You are not an existing member of this group....");
		}
		return chat;
	}

	@Override
	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException {

		Chat chat = chatRepo.findById(chatId).orElseThrow(() -> new ChatException("chat not found with id :" + chatId));
		User user = userService.findUserByUserId(userId);

		if (chat.getAdmins().contains(reqUser)) {
			chat.getUsers().remove(user);
			return chatRepo.save(chat);
		} else if (chat.getUsers().contains(reqUser)) {
			if (user.getId().equals(reqUser.getId())) {
				chat.getUsers().remove(user);
				return chatRepo.save(chat);
			}
		}

		throw new UserException("Sorry you can't remove another user");

	}

	@Override
	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
		
		Chat chat = chatRepo.findById(chatId).orElseThrow(() -> new ChatException("chat not found with id :" + chatId));
		chatRepo.deleteById(chat.getId());
		
	}

}
