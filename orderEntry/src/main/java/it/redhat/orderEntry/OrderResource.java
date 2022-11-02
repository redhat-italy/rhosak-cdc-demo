package it.redhat.orderEntry;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/order")
public class OrderResource {

    private final Logger logger = Logger.getLogger(OrderResource.class);

    @Inject
    ObjectMapper mapper;

    @Inject
    OrderRepository orderRepository;

    @POST
    @Path("/add")
    @Transactional
    public Response addJson(String json) {
        Order order = new Order();
        try {
            logger.infof("Place new Order: %s", json);
            order = mapper.readValue(json, Order.class);
            orderRepository.persist(order);
            return Response.status(Response.Status.OK).entity("Order ["+order.getID()+"] registered").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @POST
    @Path("/add/post")
    @Transactional
    public Response addPost(@FormParam("type") String type, @FormParam("size") String size, @FormParam("delivery") String delivery) {
        Order order = new Order(type,size,delivery);
        orderRepository.persist(order);
        return Response.ok(order).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAll() {
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(orderRepository.loadAll());
        return Response.status(Response.Status.OK).entity(result).build();
    }
}