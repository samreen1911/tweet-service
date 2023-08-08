package com.tweetapp.tweetservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Not found exception
 * 
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found!")
public class NotFoundException extends RuntimeException {

	/**
	 * 
	 * Notfound exception
	 * 
	 */
	public NotFoundException() {
		super();
	}

	/**
	 * 
	 * Not found exception with message
	 * 
	 */
	public NotFoundException(String message) {
		super(message);
	}

}
