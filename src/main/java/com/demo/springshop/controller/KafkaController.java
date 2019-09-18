package com.demo.springshop.controller;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.node.ObjectNode;
import com.demo.springshop.kafka.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.StopWatch;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.*;

@RestController
public class KafkaController {
    private static final String TOPIC = "couch-kafka";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/sync")
    public String syncKafka() throws ExecutionException, InterruptedException, JsonProcessingException {
        StopWatch stopWatch = new StopWatch("Main SW");

        String key = UUID.randomUUID().toString();
        String valueJson = objectMapper.writeValueAsString(randomWeatherReport());
        this.kafkaProducer.syncPublishMessage(TOPIC, key, valueJson);

        System.out.println(stopWatch.toString());

        return "Success!!!";
    }

    @GetMapping("/async")
    public String kafka(@RequestParam String count) throws InterruptedException, ExecutionException, JsonProcessingException {
        List<Map<String, String>> jsonList = createData(Integer.valueOf(count));
        CountDownLatch latch = new CountDownLatch(jsonList.size());

        StopWatch stopWatch = new StopWatch("Main SW");
        StopWatch pStopWatch = new StopWatch("Producer SW");
        stopWatch.start();
        pStopWatch.start();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletableFuture.runAsync(()->{
            jsonList.stream().parallel().forEach(v -> {
                String key = v.get("key");
                String value = v.get("value");

                this.kafkaProducer.asyncPublishMessage(TOPIC, key, value,
                        (recordMetadata, exception) -> {
                            if (recordMetadata != null) {
                                System.out.println(">>> Async Publish : Thread Name=["+Thread.currentThread().getName()+"], Partitions=["+recordMetadata.partition()+"], Offset=["+recordMetadata.offset()+"]");
                                latch.countDown();
                            } else {
                                exception.printStackTrace();
                            }
                        });
                System.out.println(">>> After: Thread Name=["+Thread.currentThread().getName()+"], key=["+key+"]");

            });
        },executor).thenRun(() -> {
            stopWatch.stop();
            System.out.println(stopWatch.toString());
        });

        latch.await();
        pStopWatch.stop();
        System.out.println(pStopWatch.toString());

        return "Success!!!";
    }

    @GetMapping("/springkafka")
    public String callWithSpringKafka(@RequestParam String count) throws InterruptedException, JsonProcessingException {
        List<Map<String, String>> jsonList = createData(Integer.valueOf(count));
        CountDownLatch latch = new CountDownLatch(jsonList.size());

        StopWatch stopWatch = new StopWatch("Main SW");
        StopWatch pStopWatch = new StopWatch("Producer SW");
        stopWatch.start();
        pStopWatch.start();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        CompletableFuture.runAsync(()->{
            jsonList.stream().parallel().forEach(v -> {
                String key = v.get("key");
                String value = v.get("value");

                ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(new ProducerRecord<>(TOPIC, key, value));
                future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        try {
                            System.out.println(">>> Async Publish : Thread Name=["+Thread.currentThread().getName()+"], Partitions=["+ future.get().getRecordMetadata().partition()+"], Offset=["+future.get().getRecordMetadata().offset()+"]");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(Throwable ex) {
                        ex.printStackTrace();
                    }
                });

                System.out.println(">>> After: Thread Name=["+Thread.currentThread().getName()+"], key=["+key+"]");

            });
        },executor).thenRun(() -> {
            stopWatch.stop();
            System.out.println(stopWatch.toString());
        });

        return "Success!!!";
    }

    private List<Map<String, String>> createData(int loopCnt) throws JsonProcessingException {
        List<Map<String, String>> jsonList = new ArrayList<>();
        for (int i=0; i < loopCnt; i++) {
            Map<String, String> hm = new LinkedHashMap<>();
            hm.put("key", String.valueOf(i));
            hm.put("value", objectMapper.writeValueAsString(randomWeatherReport()));
            jsonList.add(hm);
        }
        return jsonList;
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
