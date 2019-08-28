package com.demo.springshop.kafka;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

public interface Kafka {
    public void publishMessage(String topicName, String key, byte[] valueJson) throws JsonProcessingException, ExecutionException, InterruptedException ;
}
