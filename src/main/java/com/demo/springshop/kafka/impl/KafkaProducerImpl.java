package com.demo.springshop.kafka.impl;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.springshop.kafka.KafkaProducer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducerImpl implements KafkaProducer {

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
