package com.demo.springshop.kafka.impl;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.node.ObjectNode;
import com.demo.springshop.kafka.Kafka;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducerImpl {

    public void publishMessage(Producer<String, byte[]> producer, String topicName, byte[] valueJson) throws JsonProcessingException, ExecutionException, InterruptedException {
        String key = UUID.randomUUID().toString();

        ProducerRecord<String, byte[]> record = new ProducerRecord<>(topicName, key, valueJson);

        RecordMetadata md = producer.send(record).get();
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
