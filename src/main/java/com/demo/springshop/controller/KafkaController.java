package com.demo.springshop.controller;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.node.ObjectNode;
import com.demo.springshop.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class KafkaController {
    private static final String TOPIC = "couch-kafka";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/sync")
    public String syncKafka() throws ExecutionException, InterruptedException, JsonProcessingException {
        long start = System.currentTimeMillis();

        String key = UUID.randomUUID().toString();
        String valueJson = objectMapper.writeValueAsString(randomWeatherReport());
        this.kafkaProducer.syncPublishMessage(TOPIC, key, valueJson);

        return "응답시간 : "+String.valueOf(System.currentTimeMillis()-start)+"ms";
    }

    @GetMapping("/async")
    public String kafka() throws InterruptedException, ExecutionException, JsonProcessingException {
        List<String> jsonList =  new ArrayList<>();
        for (int i=0; i < 10000; i++) { jsonList.add(objectMapper.writeValueAsString(randomWeatherReport())); }

        long start = System.currentTimeMillis();

//        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture.runAsync(()->{
            jsonList.parallelStream().forEach(value -> {
                String key = UUID.randomUUID().toString();
                System.out.println(">>> Before: Thread Name=["+Thread.currentThread().getName()+"], key=["+key+"], value=["+value+"]");

                this.kafkaProducer.asyncPublishMessage(TOPIC, key, value,
                        (recordMetadata, exception) -> {
                            if (recordMetadata != null) {
                                //System.out.println(">>> Async Publish: partition=[" + recordMetadata.partition() + "], offset=[" + recordMetadata.offset() + "]");
                                System.out.println(">>> Finish: Thread Name=["+Thread.currentThread().getName()+"], key=["+key+"], value=["+value+"]");
                            } else {
                                exception.printStackTrace();
                            }
                        });
                System.out.println(">>> After: Thread Name=["+Thread.currentThread().getName()+"], key=["+key+"], value=["+value+"]");
            });
        }).thenRun(() -> System.out.println("완료시간: ["+(System.currentTimeMillis()-start)+"ms]"));
//        ,executor);

        System.out.println(">>> Sucess!!!");

        return "응답시간 : "+String.valueOf(System.currentTimeMillis()-start)+"ms";
    }

    private ObjectNode randomWeatherReport() {
        List<String> airports = Arrays.asList("SFO", "YVR", "LHR", "CDG", "TXL", "VCE", "DME", "DEL", "BJS");
        ObjectNode report = objectMapper.createObjectNode();
        report.put("airport", airports.get(random.nextInt(airports.size())));
        report.put("degreesF", 70 + (int) (random.nextGaussian() * 20));
        report.put("timestamp", System.currentTimeMillis());
        return report;
    }
}
