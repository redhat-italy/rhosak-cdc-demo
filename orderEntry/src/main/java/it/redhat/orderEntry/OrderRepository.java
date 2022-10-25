package it.redhat.orderEntry;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@Traced
public class OrderRepository implements PanacheRepository<Order> {

    public void persist(Order order){
        order.persistAndFlush();
    }


    public Order load(String title){
        return Order.find("title", title).firstResult();
    }

    public List<Order> loadAll(){
        return Order.listAll();
    }

}
