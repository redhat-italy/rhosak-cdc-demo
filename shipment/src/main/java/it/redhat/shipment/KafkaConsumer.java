package it.redhat.shipment;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Path("/messages")
public class KafkaConsumer {

    private final Logger logger = Logger.getLogger(KafkaConsumer.class);

    private List<String> messages = new ArrayList<String>();

    @Incoming("channel-in")
    public void receive(String record) {
        messages.add(record);
        logger.infof("Rececived Message from Kafka: %s", record);
    }


    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAll() {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(messages);
        messages.clear();
        return Response.status(Response.Status.OK).entity(result).build();
    }
}