package com.example.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStreamsJavaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamsJavaApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		
//		Properties properties = new Properties();
//		properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-application");
//		properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//		properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		
//		KafkaStreams kafkaStreams = new KafkaStreams(topology(), properties);
//		kafkaStreams.start();
//		
//		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
//	}
//
//	private Topology topology() {
//		
//
//		
//		StreamsBuilder streamsBuilder = new StreamsBuilder();
//		
//		KStream<String, String> kStream = streamsBuilder.stream("word-count-input");
//		
//		KTable<String, Long> count = kStream.mapValues(x -> x.toLowerCase())
//		.flatMapValues(x -> Arrays.asList(x.split(" ")))
//		.selectKey((key,word) -> word)
//		.groupByKey()
//		.count(Materialized.as("Counts"));
//		
//		
//		count.toStream().to("word-count-output", Produced.with(Serdes.String(), Serdes.Long()));
//		
//		return streamsBuilder.build();
//	}
	
	@Override
	public void run(String... args) throws Exception {
	

		
		Properties properties = new Properties();
		properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "color-count-application");
		properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		KafkaStreams kafkaStreams = new KafkaStreams(colorTopology(), properties);
		kafkaStreams.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
	
	}

	private Topology colorTopology() {
		StreamsBuilder builder = new StreamsBuilder();
		KStream<String, String> stream = builder.stream("color-count-input");

		KStream<String, String> filter = stream.selectKey((k,v) -> v.split(",")[0].toLowerCase())
				.mapValues(v -> v.split(",")[1].toLowerCase())
				.filter((k, v) -> v.equals("red") || v.equals("green") || v.equals("blue"));
		
		filter.to("color-count-intermediate");
		
		KTable<String, String> table = builder.table("color-count-intermediate");
		
		KTable<String, Long> count = table.groupBy((k,v) -> new KeyValue<String,String>(v, v)).count(Materialized.as("CountByColor"));

		count.toStream().to("color-count-output", Produced.with(Serdes.String(), Serdes.Long()));

		return builder.build();
	}

}
