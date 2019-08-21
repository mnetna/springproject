package com.demo.springshop.controller;

import com.demo.springshop.kafka.impl.KafkaProducerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducerImpl kafkaProducer;

    @GetMapping("/kafka")
    public String kafka() {
        System.out.println("Kafka Producer Execute!!!");
        kafkaProducer.callProducer();
        return "Kafka Producer Execute";
    }
}
