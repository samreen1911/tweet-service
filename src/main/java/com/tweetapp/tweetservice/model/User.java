package com.tweetapp.tweetservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private String username;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private String password;
	private String contactNumber;
	private String avatar;

}
