package com.learn.learnSpring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class LearnSpringApplication {

	public static void main(String[] args) {
		System.out.println("Hello Learning Project.,,ok");
		Logger logger= LoggerFactory.getLogger(LearnSpringApplication.class);
		logger.info("Hi this spring boot...");

		SpringApplication.run(LearnSpringApplication.class, args);
	}


}
