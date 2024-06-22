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
public class ProducerKeysDemo {
    private final Logger logger = LoggerFactory.getLogger(ProducerKeysDemo.class);

    public static void main(String[] args) {
        SpringApplication.run(ProducerKeysDemo.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(KafkaProducer<String, String> producer) {
        return args -> {
            // create topic
            String topic1Name = "topic-1";

            // create record

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 5; i++) {

                    String key = "id_" + i;
                    String value = "Hello " + i;

                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic1Name, key, value);
                    // send data
                    producer.send(producerRecord, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            // execute everytime send message successfully or exception is thrown
                            if (e == null) {
                                logger.info("Send message successfully:" +
                                        " Key: " + key +
                                        " | Value: " + value +
                                        " | Partition: " + recordMetadata.partition());
                            } else {
                                logger.error("Error while producing: " + e);
                            }
                        }
                    });
                }
            }

            // producer send all data and block until done - synchronous
            producer.flush();

            // flush and close producer
            producer.close();
        };
    }
}
