package com.tweetapp.tweetservice.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetDTO {

	private String tweetId;
	private String parentTweetId;
	private String firstName;
	private String lastName;
	private String username;
	private Date date;
	private String message;
	private List<String> likes; // Stores username of those who liked the Tweet.
	private List<TweetDTO> replies;
	private int clickCounter;

}
