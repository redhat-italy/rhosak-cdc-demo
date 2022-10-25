package it.redhat.orderEntry;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name="Orders")
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String size;
    private String delivery;

    public Order(){
    };

    public Order(String type, String size, String deliveryType) {

        this.type = type;
        this.size = size;
        this.delivery = deliveryType;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }




    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", deliveryType='" + delivery + '\'' +
                '}';
    }

    public Long getID() {
        return id;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}