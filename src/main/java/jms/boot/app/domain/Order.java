package jms.boot.app.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Rashad on 2/26/2018.
 */
@Data
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;
    private String name;
    private String address;
    private double price;

} 
