package com.demo.springshop.kafka.impl;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.demo.springshop.kafka.Kafka;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducerImpl implements Kafka {

    @Autowired
    private Producer producer;

    public void publishMessage(String topicName, String key, byte[] valueJson) throws JsonProcessingException, ExecutionException, InterruptedException {
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topicName, key, valueJson);
        RecordMetadata md = (RecordMetadata) producer.send(record).get();
        System.out.println("Published: {"+md.topic()+"}, {"+md.partition()+"}, {"+md.offset()+"} key: {"+key+"}");
    }

//    private class ProducerCallback implements Callback {
//        @Override
//        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//            if (e != null) {
//                e.printStackTrace();
//            }
//        }
//    }
}
