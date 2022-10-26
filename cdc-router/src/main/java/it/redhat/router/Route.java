package it.redhat.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.support.builder.Namespaces;
import org.w3c.dom.NodeList;

import java.net.ConnectException;

public class Route extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("kafka:{{kafka.source.topic.name}}")
        .routeId("From.{{kafka.source.topic.name}}2Delivery")
        .log("Received Message From: {{kafka.source.topic.name}}  ${body}")

        .choice()
        .when(body().contains("pickup"))
            .log("Delivery type pickup send to topci {{kafka.sink-pickup.topic.name}}")
            .to("kafka:{{kafka.sink-pickup.topic.name}}")
        .when(body().contains("shipment"))
            .log("Delivery type shipment send to topic {{kafka.sink-shipment.topic.name}}")
            .to("kafka:{{kafka.sink-shipment.topic.name}}")
        .otherwise()
            .log("Not Found");


        from("rest:get:pickup")
                .setBody(constant("{  \"type\": \"fruits\",  \"size\": \"small\",  \"delivery\": \"pickup\"}\n"))          // Message to send
                //.setHeader(KafkaConstants.KEY, constant("router")) // Key of the message
                .to("kafka:{{kafka.source.topic.name}}");
        from("rest:get:ship")
                .setBody(constant("{  \"type\": \"fruits\",  \"size\": \"small\",  \"delivery\": \"shipment\"}\n"))          // Message to send
                //.setHeader(KafkaConstants.KEY, constant("router")) // Key of the message
                .to("kafka:{{kafka.source.topic.name}}");
    }
}
