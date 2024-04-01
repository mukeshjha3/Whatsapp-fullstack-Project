package com.whatsapp.service;
import java.util.List;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Chat;
import com.whatsapp.model.User;
import com.whatsapp.request.GroupChatRequest;

public interface ChatService {

	public Chat createChat(User reqUser, Integer userId2) throws UserException;

	public Chat findChatByChatId(Integer ChatId) throws ChatException;

	public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

	public Chat createGroup(GroupChatRequest req, User reqUser) throws UserException;

	public Chat addUserToGroup(Integer userId, Integer chatId,User requser) throws UserException, ChatException;

	public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws ChatException, UserException;

	public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws ChatException, UserException;

	public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

}
