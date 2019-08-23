package com.demo.springshop.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Kafka {
    public void callProducer() throws JsonProcessingException;
}
