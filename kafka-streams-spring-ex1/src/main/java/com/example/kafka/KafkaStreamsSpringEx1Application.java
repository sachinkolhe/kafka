package com.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaStreamsSpringEx1Application {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamsSpringEx1Application.class, args);
	}

}
