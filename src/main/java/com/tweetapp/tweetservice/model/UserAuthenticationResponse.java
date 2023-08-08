package com.tweetapp.tweetservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * 
 * Authentication response
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserAuthenticationResponse {
	/*
	 * 
	 * check for username
	 * 
	 */
	private String username;// username
	/*
	 * 
	 * check is it valid
	 * 
	 */
	private boolean isValid;// boolean to check it is valid or not

}
