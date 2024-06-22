package com.trungvq.kafkastephaneexample;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProducerDemo {

    public static void main(String[] args) {
        SpringApplication.run(ProducerDemo.class, args);
    }

//    @Bean
    CommandLineRunner commandLineRunner(KafkaProducer<String, String> producer) {
        return args -> {
            // create topic
            String topic1Name = "topic-1";

            // create record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic1Name, "ddd");

            // send data
            producer.send(producerRecord);

            // producer send all data and block until done - synchronous
            producer.flush();

            // flush and close producer
            producer.close();
        };
    }
}
