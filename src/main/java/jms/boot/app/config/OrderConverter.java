package jms.boot.app.config;

import jms.boot.app.domain.Order;
import jms.boot.app.domain.OrderDTO;
import jms.boot.app.tools.OrderFunctions;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements OrderFunctions<Order, Long, OrderDTO> {

    @Override
    public OrderDTO overrideMethod(Order order, Long requestID) {
        return OrderDTO.builder()
                .address(order.getAddress())
                .name(order.getName())
                .price(order.getPrice())
                .orderId(order.getOrderId())
                .requestId(requestID)
                .build();
    }
}
