package com.example.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSquareConsumer {

	@KafkaListener(topics = "${kafka.topic.even-output}")
	public void consume(Long number) {
		System.out.println(String.format("Consumer :: %d", number));
	}
}
