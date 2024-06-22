package com.trungvq.kafkastephaneexample.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Value("${bootstrap.servers}")
    private String bootstrapServer;
    @Value("${sasl.mechanism}")
    private String saslMechanism;
    @Value("${security.protocol}")
    private String securityProtocol;
    @Value("${sasl.jaas.config}")
    private String saslJaasConfig;

    @Bean
    KafkaProducer<String, String> producer() {
        // create Properties
        Properties props = new Properties();

        // connect to local cluster
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // connect to Conduktor Playground
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put("sasl.mechanism", saslMechanism);
        props.put("security.protocol", securityProtocol);
        props.put("sasl.jaas.config", saslJaasConfig);

        // set producer props
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new KafkaProducer<>(props);
    }
}
