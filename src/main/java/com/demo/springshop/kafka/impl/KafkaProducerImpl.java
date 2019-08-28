package com.demo.springshop.kafka.impl;

import com.demo.springshop.kafka.Kafka;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducerImpl implements Kafka {

    @Autowired
    private Producer producer;

    @Override
    public void publishMessage(String topicName, String key, byte[] valueJson) throws ExecutionException, InterruptedException {
        RecordMetadata recordMetadata = (RecordMetadata) producer.send(new ProducerRecord<>(topicName, key, valueJson)).get();
    }

    @Override
    public void publishMessageWithCallback(String topicName, String key, byte[] valueJson, Callback callback) {
        producer.send(new ProducerRecord<>(topicName, key, valueJson), callback);
    }

    @Override
    public void close() {
        producer.flush();
        producer.close();
    }
}
