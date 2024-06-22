package com.trungvq.kafkastephaneexample;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProducerCallbackDemo {
    private final Logger logger = LoggerFactory.getLogger(ProducerCallbackDemo.class);

    public static void main(String[] args) {
        SpringApplication.run(ProducerCallbackDemo.class, args);
    }

//    @Bean
    CommandLineRunner commandLineRunner(KafkaProducer<String, String> producer) {
        return args -> {
            // create topic
            String topic1Name = "topic-1";

            // create record
            for (int i = 0; i < 10; i++) {
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic1Name, "Hello " + i);
                // send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        // execute everytime send message successfully or exception is thrown
                        if (e == null) {
                            logger.info("Send message successfully:\n" +
                                    "Topic: " + recordMetadata.topic() + "\n" +
                                    "Partition: " + recordMetadata.partition() + "\n" +
                                    "Offset: " + recordMetadata.offset() + "\n" +
                                    "Timestamp: " + recordMetadata.timestamp());
                        } else {
                            logger.error("Error while producing: " + e);
                        }
                    }
                });
            }

            // producer send all data and block until done - synchronous
            producer.flush();

            // flush and close producer
            producer.close();
        };
    }
}
