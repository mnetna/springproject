package com.demo.springshop.kafka;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.Callback;

import java.util.concurrent.ExecutionException;

public interface KafkaProducer {
    public void syncPublishMessage(String topicName, String key, byte[] valueJson) throws ExecutionException, InterruptedException ;
    public void asyncPublishMessage(String topicName, String key, byte[] valueJson, Callback callback);
    public void close();
}
