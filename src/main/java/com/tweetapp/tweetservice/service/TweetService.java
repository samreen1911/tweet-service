package com.tweetapp.tweetservice.service;

import java.util.List;

import com.tweetapp.tweetservice.dto.TweetDTO;
import com.tweetapp.tweetservice.model.Tweet;

public interface TweetService {

	List<Tweet> getAllTweets();

	Tweet addTweet(String username, TweetDTO tweetDTO, String token);

	Tweet updateTweet(String username, String tweetId, TweetDTO tweetDTO);

	String deleteTweet(String username, String tweetId);

	Tweet toggleLikeTweet(String username, String tweetId);

	Tweet replyTweet(String username, String tweetId, TweetDTO reply, String token);

	List<Tweet> getAllTweetsByUsername(String username);

	Tweet getTweet(String tweetId);

	Tweet getClickCountTweet(String tweetId);

}
