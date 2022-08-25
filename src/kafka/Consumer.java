package kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class Consumer {
    
    private static Properties getKafkaProperties(){
        Properties p = Settings.getKafkaProperties();
        p.put("group.id", "GROUP1");
        p.put("enable.auto.commit", "false");
        p.put("session.timeout.ms", "30000");
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        p.put("auto.offset.reset", "earliest");
        return p;
    }

    public static void main(String[] args) {
        final KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(getKafkaProperties());;
        consumer.subscribe(Collections.singletonList(Settings.streamName));
        ConsumerRecords<Integer, String> records = consumer.poll(10000);

        System.out.println("size of records polled is "+ records.count());
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
        }

        consumer.commitSync();
        consumer.close();
    }
}
