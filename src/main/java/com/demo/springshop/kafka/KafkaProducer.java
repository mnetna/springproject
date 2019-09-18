package com.demo.springshop.kafka;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.Callback;

import java.util.concurrent.ExecutionException;

public interface KafkaProducer<K, V> {
    public void syncPublishMessage(String topicName, K key, V valueJson) throws ExecutionException, InterruptedException ;
    public void asyncPublishMessage(String topicName, K key, V valueJson, Callback callback);
}
