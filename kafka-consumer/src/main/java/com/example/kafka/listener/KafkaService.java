package com.example.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.kafka.dto.User;

@Service
public class KafkaService {

//	@KafkaListener(topics = "kafka_example", groupId = "group_id")
//	public void consume(String message) {
//		System.out.println("Consumed message: " + message);
//	}

	@KafkaListener(topics = "di_topic_1", groupId = "group_json", containerFactory = "kafkaListenerContainerFactory")
	public void consumeJson(User user) {
		try {
		System.out.println("Consumed JSON Message: " + user);
		}catch(Exception e) {
			System.out.println("error printing message");
		}
	}
}
