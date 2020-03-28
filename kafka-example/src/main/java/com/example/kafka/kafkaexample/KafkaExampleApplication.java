package com.example.kafka.kafkaexample;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaExampleApplication implements CommandLineRunner{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(KafkaExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Producer Demo
		
//		String bootstrapServer = "localhost:9092";
//		Properties properties = new Properties();
//		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
//		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//		
//		
//		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
//		for(int i=0;i<5;i++) {
//			String topic = "first_topic";
//			String value = "hello world from java callback.." + Integer.toString(i);
//			String key = "id_"+Integer.toString(i);
//			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic,key,value);
//			logger.info("key : " + key);
//			producer.send(record, new Callback() {
//				
//				@Override
//				public void onCompletion(RecordMetadata metadata, Exception exception) {
//					if (exception == null) {
//						logger.info(" Received new metadata .\n" + "Topic: " + metadata.topic() + "\n" + "Partition: "
//								+ metadata.partition() + "\n" + "Offset: " + metadata.offset() + "\n" + "TimeStamp: "
//								+ metadata.timestamp());
//
//					} else {
//						logger.error("Error while producing..");
//					}
//				}
//			});
//		}
//	
//		
//		producer.flush();
//		producer.close();
		
		/**************************************************************************************************/
		
		
		String bootstrapServer = "localhost:9092";
		String groupId = "my_fourth_application";
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		//creating consumer
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties); 
		
		//subscribe to topic
		consumer.subscribe(Arrays.asList("first_topic"));
		
		
		while(true) {
			
			ConsumerRecords<String,String> consumerRecords = consumer.poll(Duration.ofMillis(100));
			
			for(ConsumerRecord<String, String> record : consumerRecords) {
				logger.info(" Key : {} \t Value : {} \n Partition : {} \t Offset : {} \n ", record.key(),
						record.value(), record.partition(), record.offset());
			}
		}
			
		
		//consumer.close();
		
	}

}
