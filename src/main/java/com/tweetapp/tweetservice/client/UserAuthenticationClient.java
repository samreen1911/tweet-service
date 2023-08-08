package com.tweetapp.tweetservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.tweetapp.tweetservice.model.UserAuthenticationResponse;
import com.tweetapp.tweetservice.model.Users;

/**
 * 
 * Authentication feign client
 *
 */
//@FeignClient(name = "user-service", url = "${auth.client.url:http://localhost:8081/api/v1.0/tweets}")
@FeignClient(name = "user-service", url = "http://localhost:9091/api/v1.0/tweets")
public interface UserAuthenticationClient {
	/**
	 * This will communicate with authorization-service with feign client to
	 * validate
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/auth/validate")
	public UserAuthenticationResponse getValidity(@RequestHeader("Authorization") String token);

	@GetMapping("/user/{username}")
	public ResponseEntity<Users> findUser(@PathVariable String username,
			@RequestHeader(name = "Authorization") String token);

	@GetMapping("user/all")
	public ResponseEntity<List<Users>> getAllUser(@RequestHeader(name = "Authorization") String token);
}
