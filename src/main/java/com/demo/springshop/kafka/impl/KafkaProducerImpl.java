package com.demo.springshop.kafka.impl;

import com.demo.springshop.kafka.Kafka;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

@Component
public class KafkaProducerImpl implements Kafka, Producer {

    private Properties props;

    private KafkaProducerImpl() {
        this.props = new Properties();
        this.props.put("bootstrap.servers", "192.168.56.1:9092");
        this.props.put("transactional.id", "my-transactional-id");
    }

    public void callProducer() {
        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
        producer.initTransactions();

        try {
            producer.beginTransaction();
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("couch-kafka", Integer.toString(i), Integer.toString(i)));
                System.out.println("kafka execute: {" + i + "}");
            }
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }

    @Override
    public void initTransactions() {

    }

    @Override
    public void beginTransaction() throws ProducerFencedException {

    }

    @Override
    public void commitTransaction() throws ProducerFencedException {

    }

    @Override
    public void abortTransaction() throws ProducerFencedException {

    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord producerRecord) {
        return null;
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord producerRecord, Callback callback) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public List<PartitionInfo> partitionsFor(String s) {
        return null;
    }

    @Override
    public Map<MetricName, ? extends Metric> metrics() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void close(Duration duration) {

    }

    @Override
    public void sendOffsetsToTransaction(Map map, String s) throws ProducerFencedException {

    }
}
