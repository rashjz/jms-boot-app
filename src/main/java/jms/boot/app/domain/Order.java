package jms.boot.app.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@Getter
@Setter
@Entity(name = "ORDERS")
//@NamedQueries({ @NamedQuery(name = "Order.getAllAddress",
//        query = "from ORDERS o where o.address = :address") })
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "price")
    private double price;

} 
