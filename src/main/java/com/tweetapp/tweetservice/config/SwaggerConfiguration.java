package com.tweetapp.tweetservice.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration

public class SwaggerConfiguration {
	@Bean
	public Docket productApi() {
		Set<String> responseProduceType = new HashSet<>();
		responseProduceType.add("application/json");
		responseProduceType.add("application/xml");
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
				.build().useDefaultResponseMessages(false).genericModelSubstitutes(ResponseEntity.class)
				.produces(responseProduceType).consumes(responseProduceType).apiInfo(apiInfo());
	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("TWEET SERVICE")
				.description("All Tweet Microservice endpoints and models reference for developers")
				.termsOfServiceUrl("https://cognizant.com").contact("samreenahmed1911@gmail.com")
				.license("Cognizant License").licenseUrl("https://cognizant.com").version("1.0").build();
	}
}