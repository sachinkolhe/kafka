package com.example.kafka.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsConfig {

	@Value("${kafka.topic.input}")
	private String inputTopic;

	@Value("${kafka.topic.even-output}")
	private String outputTopic;

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	public KafkaStreamsConfiguration kStreamsConfig(KafkaProperties kafkaProperties) {
		Map<String, Object> config = new HashMap<>();
		config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
		config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass());
		return new KafkaStreamsConfiguration(config);
	}

	@Bean
	public KStream<String, Long> kStream(StreamsBuilder builder) {
		// here we are writing a topology
		KStream<String, Long> stream = builder.stream(inputTopic);
		stream.filter((k, v) -> v % 2 == 0).mapValues(v -> v * v).to(outputTopic);
		return stream;
	}

}
