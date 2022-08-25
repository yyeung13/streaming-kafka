package kafka;

import java.util.Properties;

class Settings 
{
    // To configure
    static String bootstrapServers = "cell-1.streaming.eu-frankfurt-1.oci.oraclecloud.com:9092";
    static String authToken = "X9auxxxxxxxxxx";
    static String tenancyName = "tenantname";
    static String username = "oracleidentitycloudservice/name@domain.com";
    static String streamPoolId = "ocid1.streampool.oc1.eu-frankfurt-1.amaaaaaauxxxxxxxxxxxxxxxxxxx";
    static String authToken = "X9auxxxxxxxxxx";

    static String streamName = "topic"; // This is a topic in Kafka 

    static public Properties getKafkaProperties() {
        Properties p = new Properties();
        p.put("bootstrap.servers", Settings.bootstrapServers);
        p.put("security.protocol", "SASL_SSL");
        p.put("sasl.mechanism", "PLAIN");

        final String value = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\""
                + tenancyName + "/"
                + username + "/"
                + streamPoolId + "\" "
                + "password=\""
                + authToken + "\";";
        p.put("sasl.jaas.config", value);

        return p;
    }
}
