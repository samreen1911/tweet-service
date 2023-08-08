package com.tweetapp.tweetservice.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	@Id
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String contactNumber;
	private String avatar;

}
