package com.example.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.dto.User;

@RestController
@RequestMapping("/kafka")
public class UserResource {

	@Autowired
	KafkaTemplate<String, User> kafkaTemplate;
	private static final String TOPIC = "di_topic_1";
	
	
	@PostMapping("/publish/message")
	public String postMessage(@RequestBody User message) {
		
		kafkaTemplate.send(TOPIC , message);
		return "Message published successfully.";
	}
	
}
