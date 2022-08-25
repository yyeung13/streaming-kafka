package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    private static Properties getKafkaProperties() {
        Properties p = Settings.getKafkaProperties();
        p.put("enable.idempotence", false);
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        p.put("retries", 3); // retries on transient errors and load balancing disconnection
        p.put("max.request.size", 1024 * 1024); // limit request size to 1MB
        return p;
    }

    public static void main(String args[]) {
            Properties properties = getKafkaProperties();
            KafkaProducer producer = new KafkaProducer<>(properties);

            for(int i=0;i<10;i++) {
                ProducerRecord<String, String> record = new ProducerRecord<>(Settings.streamName, "message" + i, "{ i=" +i +" }" );
                producer.send(record, (md, ex) -> {
                    if (ex != null) {
                        System.err.println("exception occurred in producer for review :" + record.value()
                                + ", exception is " + ex);
                        ex.printStackTrace();
                    } else {
                        System.err.println("Sent msg to " + md.partition() + " with offset " + md.offset() + " at " + md.timestamp());
                    }
                });
            }
            // producer.send() is async, to make sure all messages are sent we use producer.flush()
            producer.flush();
            producer.close();
        
    }
}
