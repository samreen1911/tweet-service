package com.tweetapp.tweetservice.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.tweetservice.model.Tweet;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

	/**
	 * to fetch all tweets by userId
	 * 
	 * @param userId
	 * @return
	 */
	// @Query(value = "select * from tweet where userId=:userId")
	List<Tweet> findByUsername(String username);
}
