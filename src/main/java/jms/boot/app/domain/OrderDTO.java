package jms.boot.app.domain;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;
    private Long requestId;
    private String name;
    private String address;
    private double price;
    private String note;

}
