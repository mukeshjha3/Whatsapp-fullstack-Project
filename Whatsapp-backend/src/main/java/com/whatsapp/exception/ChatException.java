package com.whatsapp.exception;

import java.io.Serializable;

public class ChatException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

	public ChatException(String message) {
		super(message);
	}
}
