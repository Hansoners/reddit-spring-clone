package com.example.springredditdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringRedditDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRedditDemoApplication.class, args);
	}

}
