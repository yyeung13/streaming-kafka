package kafka;

import java.util.Properties;

class Settings_DLQ
{
    // To configure
    static String bootstrapServers = "cell-1.streaming.ap-singapore-1.oci.oraclecloud.com:9092";
    static String streamPoolId = "ocid1.streampool.oc1.ap-singapore-1.amaaaaaagoffsvaasyjn2z2kw5grqh3tymwibwf3td43mv4mz2ddpysst7fq";
    static String tenancyName = "bbtrial";
    static String username = "y.yeung@oracle.com";
    static String authToken = "PSDq8Szt0{{giEL7tJ<)";

    static String streamName = "topic_dlq"; // This is a topic in Kafka 

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
