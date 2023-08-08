package com.tweetapp.tweetservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.tweetservice.client.UserAuthenticationClient;
import com.tweetapp.tweetservice.dto.TweetDTO;
import com.tweetapp.tweetservice.exception.LoginFailedException;
import com.tweetapp.tweetservice.exception.NotFoundException;
import com.tweetapp.tweetservice.model.Tweet;
import com.tweetapp.tweetservice.model.UserAuthenticationResponse;
import com.tweetapp.tweetservice.model.Users;
import com.tweetapp.tweetservice.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1.0/tweets")
@CrossOrigin(origins = "*")
public class TweetController {
	@Autowired
	private UserAuthenticationClient userAuthenticationClient;
	@Autowired
	private TweetService tweetService;

	String loginSuccess = "Successfully Logged in";
	
	@GetMapping(value = "/health")
	public ResponseEntity<?> healthCheckMethod() {
		log.info("Health check method");
		return ResponseEntity.ok("Tweet Service health check successful...");
	}

	@GetMapping("/all")
	public ResponseEntity<List<Tweet>> getAllTweets(@RequestHeader(name = "Authorization") String token)
			throws LoginFailedException {
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid()) {
			log.info(loginSuccess);

			log.info("Showing all tweets!");
			List<Tweet> tweets = tweetService.getAllTweets();

			if (tweets.isEmpty()) {
				return new ResponseEntity<>(tweets, HttpStatus.OK);
			}
			return new ResponseEntity<>(tweets, HttpStatus.OK);

		} else {
			log.info("Method getAllTweets failed!");
			throw new LoginFailedException();
		}
	}

	@GetMapping("/getTweet/{tweetId}")
	public ResponseEntity<Tweet> getTweet(@PathVariable String tweetId,
			@RequestHeader(name = "Authorization") String token) throws LoginFailedException {
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid()) {
			log.info(loginSuccess);

			log.info("Showing particular tweets!");
			Tweet tweet = tweetService.getTweet(tweetId);
			if (tweet == null) {
				throw new NotFoundException();
			}
			return new ResponseEntity<>(tweet, HttpStatus.OK);

		} else {
			log.info("Method getTweet failed!");
			throw new LoginFailedException();
		}
	}

	@GetMapping("/click/{tweetId}")
	public ResponseEntity<Tweet> getClickCountTweet(@PathVariable String tweetId,
			@RequestHeader(name = "Authorization") String token) throws LoginFailedException {
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid()) {
			log.info(loginSuccess);

			log.info("Showing click counter for tweet!");
			Tweet tweet = tweetService.getClickCountTweet(tweetId);
			if (tweet == null) {
				throw new NotFoundException();
			}
			return new ResponseEntity<>(tweet, HttpStatus.OK);

		} else {
			log.info("Method getClickCountTweet failed!");
			throw new LoginFailedException();
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<List<Tweet>> getAllTweetsByUsername(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username) {
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid()) {

			List<Tweet> tweets;

			log.info(loginSuccess);

			log.info("Showing all tweets of particular user!");
			tweets = tweetService.getAllTweetsByUsername(username);
			if (tweets.isEmpty()) {
				return new ResponseEntity<>(tweets, HttpStatus.OK);
			}
			return new ResponseEntity<>(tweets, HttpStatus.OK);

		} else {
			log.info("Method getAllTweetsByUsername failed!");
			throw new LoginFailedException();
		}
	}

	@PostMapping("/{username}/add")
	public ResponseEntity<Tweet> postTweet(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username, @RequestBody TweetDTO tweetDTO) {
		Tweet result = null;
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid() && response.getUsername().equals(username)) {
			log.info(loginSuccess);
			log.info("Post a tweet!");
			result = tweetService.addTweet(username, tweetDTO, token);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			log.info("Method postTweet failed!");
			throw new LoginFailedException();
		}
	}

	@DeleteMapping("/{username}/delete/{tweetId}")
	public ResponseEntity<String> deleteTweet(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username, @PathVariable String tweetId) {
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid() && response.getUsername().equals(username)) {
			log.info(loginSuccess);
			log.info("Delete a tweet!");
			String result = tweetService.deleteTweet(username, tweetId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			log.info("Method deleteTweet failed!");
			throw new LoginFailedException();
		}
	}

	@PutMapping("/{username}/update/{tweetId}")
	public ResponseEntity<Tweet> updateTweet(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username, @PathVariable String tweetId, @RequestBody TweetDTO tweetDTO) {
		Tweet result = null;
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid() && response.getUsername().equals(username)) {
			log.info(loginSuccess);

			log.info("Update a tweet!");
			result = tweetService.updateTweet(username, tweetId, tweetDTO);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			log.info("Method updateTweet failed!");
			throw new LoginFailedException();
		}

	}

	@PutMapping("/{username}/like/{tweetId}")
	public ResponseEntity<Tweet> likeTweet(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username, @PathVariable String tweetId) {
		Tweet result = null;
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid() && response.getUsername().equals(username)) {
			log.info(loginSuccess);
			log.info("Like a tweet!");
			result = tweetService.toggleLikeTweet(username, tweetId);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			log.info("Method likeTweet failed!");
			throw new LoginFailedException();
		}
	}

	@PostMapping("/{username}/reply/{tweetId}")
	public ResponseEntity<Tweet> replyTweet(@RequestHeader(name = "Authorization") String token,
			@PathVariable String username, @PathVariable String tweetId, @RequestBody TweetDTO reply) {
		Tweet result = null;
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid() && response.getUsername().equals(username)) {
			log.info(loginSuccess);
			log.info("Reply a tweet!");
			result = tweetService.replyTweet(username, tweetId, reply, token);
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			log.info("Method replyTweet failed!");
			throw new LoginFailedException();
		}
	}

	@GetMapping("/user/all")
	public ResponseEntity<List<Users>> getAllUser(@RequestHeader(name = "Authorization") String token)
			throws Exception {
		List<Users> users = new ArrayList<>();
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid()) {
			log.info(loginSuccess);
			log.info("fetching user details!");
			users = userAuthenticationClient.getAllUser(token).getBody();
		}
		return new ResponseEntity<>(users, HttpStatus.OK);

	}

	@GetMapping("/user/{username}")
	public ResponseEntity<Users> findUser(@PathVariable String username,
			@RequestHeader(name = "Authorization") String token) throws Exception {
		Users user = null;
		UserAuthenticationResponse response = userAuthenticationClient.getValidity(token);
		if (response.isValid()) {
			log.info(loginSuccess);
			log.info("fetching user details!");
			user = userAuthenticationClient.findUser(username, token).getBody();
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
