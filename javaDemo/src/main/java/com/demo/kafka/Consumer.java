package com.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 消费者
 */
public class Consumer {

    public static void main(String[] args) {
        //设置消费组的名称
        //将属性值反序列化
        Properties properties = new Properties();
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", "192.168.1.13:9092,192.168.1.14:9092，192.168.1.15:9092");
        properties.put("group.id", "test-consumer-group");

        try (//创建一个消费者客户端实例
             KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);) {
            //订阅主题
            consumer.subscribe(Collections.singletonList("demo"));
            //循环消费消息
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("receiver a message from consumer client:" + record.value());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
