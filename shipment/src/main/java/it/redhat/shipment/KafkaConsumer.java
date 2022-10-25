package it.redhat.shipment;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class KafkaConsumer {

    private final Logger logger = Logger.getLogger(KafkaConsumer.class);

    @Incoming("channel-in")
    public void receive(String record) {
        System.out.println(record);
        logger.infof("Rececived Message from Kafka: %s", record);
    }
}