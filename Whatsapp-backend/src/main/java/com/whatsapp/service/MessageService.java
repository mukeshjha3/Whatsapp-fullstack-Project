package com.whatsapp.service;

import java.util.List;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.model.Message;
import com.whatsapp.model.User;
import com.whatsapp.request.SendMessageRequest;

public interface MessageService {

	public Message sendMessage(SendMessageRequest req) throws UserException,ChatException;
	public List<Message> getChatMessages(Integer chatId,User reqUser) throws ChatException,UserException ;
	public Message findMessageById(Integer messageId) throws MessageException;
	public void deleteMessage(Integer messageId,User reqUser) throws MessageException,UserException;
	
}
 