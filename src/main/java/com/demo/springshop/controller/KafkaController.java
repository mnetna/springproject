package com.demo.springshop.controller;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.node.ObjectNode;
import com.demo.springshop.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaController {
    private static final String TOPIC = "couch-kafka";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/kafka")
    public String kafka() throws InterruptedException, ExecutionException, JsonProcessingException {
        String key = UUID.randomUUID().toString();

        ObjectNode weatherReport = randomWeatherReport();
        byte[] valueJson = objectMapper.writeValueAsBytes(weatherReport);

        // Just Async Publish
        //kafkaProducer.publishMessage(TOPIC, key, valueJson);

        // Callback By Async
        this.kafkaProducer.publishMessageWithCallback(TOPIC, key, valueJson,
                (recordMetadata, exception) -> {
                    if (recordMetadata != null) {
                        System.out.println("partition(" + recordMetadata.partition() + "), offset(" + recordMetadata.offset() + ")");
                    } else {
                        exception.printStackTrace();
                    }
                });

        return "Kafka Producer Execute";
    }

    private static ObjectNode randomWeatherReport() {
        List<String> airports = Arrays.asList("SFO", "YVR", "LHR", "CDG", "TXL", "VCE", "DME", "DEL", "BJS");
        ObjectNode report = objectMapper.createObjectNode();
        report.put("airport", airports.get(random.nextInt(airports.size())));
        report.put("degreesF", 70 + (int) (random.nextGaussian() * 20));
        report.put("timestamp", System.currentTimeMillis());
        return report;
    }
}
