package com.demo.springshop.kafka.impl;

import com.demo.springshop.kafka.KafkaProducer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaProducerImpl implements KafkaProducer {

    @Autowired
    private Producer producer;

    /**
     * Future 객체의 get() 메소드는 스레드를 블럭한 상태에서 Future의 리턴을 기다리기 때문에 동기 전송 방법
     * @param topicName
     * @param key
     * @param valueJson
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public void syncPublishMessage(String topicName, Object key, Object valueJson) throws ExecutionException, InterruptedException {
        RecordMetadata recordMetadata = (RecordMetadata) this.producer.send(new ProducerRecord<>(topicName, key, valueJson)).get();
//        System.out.println(">>> Sync Publish: partition=[" + recordMetadata.partition() + "], offset=[" + recordMetadata.offset() + "]");
        System.out.println(">>> Finish: key=["+key+"], value=["+valueJson+"]");
    }

    /**
     * send() 메소드후 브로커에서 응답받으면 바로 콜백을 호출하기 때문에 비동기 전송 방법
     * @param topicName
     * @param key
     * @param valueJson
     * @param callback
     */
    @Override
    public void asyncPublishMessage(String topicName, Object key, Object valueJson, Callback callback) {
        this.producer.send(new ProducerRecord<>(topicName, key, valueJson), callback);
    }
}
