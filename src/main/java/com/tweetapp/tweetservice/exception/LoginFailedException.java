package com.tweetapp.tweetservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Login failed exception
 * 
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You are not LoggedIn")
public class LoginFailedException extends RuntimeException {
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
