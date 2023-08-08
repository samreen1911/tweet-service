package com.tweetapp.tweetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableFeignClients
@ComponentScan(basePackages = "com.tweetapp.tweetservice.*")
public class TweetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetServiceApplication.class, args);
	}
}
