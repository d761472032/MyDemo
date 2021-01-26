package com.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产者
 */
public class Producer {

    public static void main(String[] args) {
        // 1.创建配置对象
        Properties properties = new Properties();

        // 2.设置配置信息
        // kafka 服务端的主机名和端口号
        properties.put("bootstrap.servers", "192.168.1.13:9092,192.168.1.14:9092，192.168.1.15:9092");
        // 等待所有副本的应答
        properties.put("acks", "all");
        // 消息发送最大的尝试次数
        properties.put("retries", 0);
        // 一批消息的处理大小
        properties.put("batch.size", 16384);
        // 请求的延迟
        properties.put("linger.ms", 1);
        // 发送缓冲区内存大小
        properties.put("buffer.size", 33554432);
        // key 序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value 序列化
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (// KafkaProducer 有多个构造方法，可以用Map来进行社会参数，也可在构造方法中进行设置序列化
             KafkaProducer<String, String> producer = new KafkaProducer<>(properties);) {
            // 3生产数据
            for (int i = 0; i < 30; i++) {
                // ProducerRecord 中还可以设置 topic partition 时间戳 header 等信息
                producer.send(new ProducerRecord<>("demo", Integer.toString(i), "helllo world-" + i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
