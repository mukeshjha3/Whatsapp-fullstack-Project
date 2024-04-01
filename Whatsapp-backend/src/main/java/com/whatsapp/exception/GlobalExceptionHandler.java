package com.whatsapp.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDetail userExceptionHandler(UserException e, WebRequest req) {
		ErrorDetail build = ErrorDetail.builder()
				.error(e.getMessage())
				.timestamp(LocalDateTime.now())
				.message(req.getDescription(false))
				.build();
		return build;
	}
	
	@ExceptionHandler(MessageException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDetail MessageExceptionHandler(MessageException e, WebRequest req) {
		ErrorDetail build = ErrorDetail.builder()
				.error(e.getMessage())
				.timestamp(LocalDateTime.now())
				.message(req.getDescription(false))
				.build();
		return build;
	}
	@ExceptionHandler(ChatException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDetail MessageExceptionHandler(ChatException e, WebRequest req) {
		ErrorDetail build = ErrorDetail.builder()
				.error(e.getMessage())
				.timestamp(LocalDateTime.now())
				.message(req.getDescription(false))
				.build();
		return build;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDetail MethooArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest req) {
		ErrorDetail build = ErrorDetail.builder()
				.error(e.getMessage())
				.timestamp(LocalDateTime.now())
				.message(req.getDescription(false))
				.build();
		return build;
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDetail handleNoHandlerFoundException(NoHandlerFoundException e, WebRequest req) {
		ErrorDetail build = ErrorDetail.builder()
				.error(e.getMessage())
				.timestamp(LocalDateTime.now())
				.message(req.getDescription(false))
				.build();
		return build;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorDetail otherExceptionHandler(Exception e, WebRequest req) {
		ErrorDetail build = ErrorDetail.builder()
				.error(e.getMessage())
				.timestamp(LocalDateTime.now())
				.message(req.getDescription(false))
				.build();
		return build;
	}
}
