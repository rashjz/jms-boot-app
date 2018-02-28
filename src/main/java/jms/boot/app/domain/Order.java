package jms.boot.app.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    private String name;
    private String address;
    private double price;

} 
