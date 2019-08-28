package com.demo.springshop.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    private static final String BOOTSTRAP_SERVERS = "192.168.56.1:9092";

    @Bean
    public Producer createProducer() {
        Properties config = new Properties();
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        config.put(ProducerConfig.ACKS_CONFIG, "1"); // 0: 모든 ack 기다리지 않음, 1: 리더의 ack만 확인, all: 모든 팔로워의 ack 확인, best: 리더1, 팔로워1 ack 확인
        //config.put(ProducerConfig.RETRIES_CONFIG, ""); // 일시적인 오류시 전송 실패 재시도 횟수
        //config.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, ""); // 프로듀서가 보내는 최대 메시지 사이즈 (기본값은 1MB)
        return new KafkaProducer(config);
    }
}
