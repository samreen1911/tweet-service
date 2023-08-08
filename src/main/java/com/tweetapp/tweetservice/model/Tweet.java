package com.tweetapp.tweetservice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String tweetId;
	private String parentTweetId;
	private String firstName;
	private String lastName;
	private String username;
	private Date date;
	private String message;
	private List<String> likes; //Stores username of those who liked the Tweet.
	private List<Tweet> replies;
	private int clickCounter;

}
