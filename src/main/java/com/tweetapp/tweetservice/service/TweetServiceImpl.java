package com.tweetapp.tweetservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.tweetservice.client.UserAuthenticationClient;
import com.tweetapp.tweetservice.dao.TweetRepository;
import com.tweetapp.tweetservice.dto.TweetDTO;
import com.tweetapp.tweetservice.exception.NotFoundException;
import com.tweetapp.tweetservice.model.Tweet;
import com.tweetapp.tweetservice.model.Users;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private UserAuthenticationClient userAuthenticationClient;

	@Autowired
	private TweetRepository tweetRepository;

	@Override
	public List<Tweet> getAllTweets() {
		List<Tweet> tweets = tweetRepository.findAll();
		Collections.reverse(tweets);
		return tweets;
	}

	String tweetNotFound = "Tweet Not Found.";

	@Override
	public Tweet addTweet(String username, TweetDTO tweetDTO, String token) {
		Users user = userAuthenticationClient.findUser(username, token).getBody();
		if (user != null) {
			Tweet tweet = new Tweet();
			tweet.setFirstName(user.getFirstName());
			tweet.setLastName(user.getLastName());
			tweet.setUsername(username);
			tweet.setDate(new Date());
			tweet.setMessage(tweetDTO.getMessage());
			tweet.setLikes(new ArrayList<>());
			tweet.setReplies(new ArrayList<>());
			tweet.setClickCounter(0);
			return tweetRepository.save(tweet);
		}
		return null;
	}

	@Override
	public Tweet updateTweet(String username, String tweetId, TweetDTO tweetDTO) {
		Tweet result = new Tweet();
		if (tweetRepository.findById(tweetId).isPresent()) {
			result = tweetRepository.findById(tweetId).orElseThrow(() -> new NotFoundException(tweetNotFound));
			result.setMessage(tweetDTO.getMessage());
			tweetRepository.save(result);

		}
		return result;
	}

	@Override
	public String deleteTweet(String username, String tweetId) {
		Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new NotFoundException(tweetNotFound));
		if (tweetRepository.findById(tweetId).isPresent() && tweet.getUsername().equals(username)) {
			tweetRepository.deleteById(tweetId);
			return "Tweet has been deleted!!!";
		}
		return "Tweet has not been deleted!!!";
	}

	@Override
	public Tweet toggleLikeTweet(String username, String tweetId) {
		Optional<Tweet> tweet = tweetRepository.findById(tweetId);
		Tweet result = new Tweet();
		if (tweet.isPresent()) {
			List<String> likes = tweet.get().getLikes();
			if (likes.contains(username)) {
				likes.remove(username);
				tweet.get().setLikes(likes);
			} else {
				likes.add(username);
				tweet.get().setLikes(likes);
			}
			result = tweetRepository.save(tweet.get());
		}
		return result;
	}

	@Override
	public Tweet replyTweet(String username, String tweetId, TweetDTO reply, String token) {
		Users user = userAuthenticationClient.findUser(username, token).getBody();
		if (user != null) {
			Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new NotFoundException(tweetNotFound));
			Tweet replyTweet = new Tweet();
			replyTweet.setTweetId(tweetId + "_reply" + (tweet.getReplies().size() + 1));
			replyTweet.setFirstName(user.getFirstName());
			replyTweet.setLastName(user.getLastName());
			replyTweet.setParentTweetId(tweetId);
			replyTweet.setUsername(username);
			replyTweet.setDate(new Date());
			replyTweet.setMessage(reply.getMessage());
			replyTweet.setLikes(new ArrayList<>());
			replyTweet.setReplies(new ArrayList<>());
			replyTweet.setClickCounter(0);
			List<Tweet> replies = tweet.getReplies();
			replies.add(0, replyTweet);
			tweet.setReplies(replies);
			return tweetRepository.save(tweet);
		}
		return null;
	}

	@Override
	public List<Tweet> getAllTweetsByUsername(String username) {
		List<Tweet> userTweets = tweetRepository.findByUsername(username);
		Collections.reverse(userTweets);
		return userTweets;
	}

	@Override
	public Tweet getTweet(String tweetId) {
		return tweetRepository.findById(tweetId).orElseThrow(() -> new NotFoundException(tweetNotFound));
	}

	@Override
	public Tweet getClickCountTweet(String tweetId) {
		Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new NotFoundException(tweetNotFound));
		tweet.setClickCounter(tweet.getClickCounter() + 1);
		return tweetRepository.save(tweet);
	}

}
