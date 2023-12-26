package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectionApplication.class, args);
	}
	
}
