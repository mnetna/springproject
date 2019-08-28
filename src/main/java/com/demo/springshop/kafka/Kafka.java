package com.demo.springshop.kafka;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.Callback;

import java.util.concurrent.ExecutionException;

public interface Kafka {
    public void publishMessage(String topicName, String key, byte[] valueJson) throws ExecutionException, InterruptedException ;
    public void publishMessageWithCallback(String topicName, String key, byte[] valueJson, Callback callback);
    public void close();
}
