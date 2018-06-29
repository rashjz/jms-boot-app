package jms.boot.app.controller;

import jms.boot.app.config.OrderConverter;
import jms.boot.app.domain.Order;
import jms.boot.app.domain.OrderDTO;
import jms.boot.app.exception.OrderNotFoundException;
import jms.boot.app.repository.OrderTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/transaction")
public class OrderTransactionController {

    private JmsTemplate jmsTemplate;
    private OrderTransactionRepository orderTransactionRepository;
    private OrderConverter orderConverter;

    @Autowired
    public OrderTransactionController(JmsTemplate jmsTemplate,
                                      OrderTransactionRepository orderTransactionRepository,
                                      OrderConverter orderConverter) {
        this.jmsTemplate = jmsTemplate;
        this.orderConverter = orderConverter;
        this.orderTransactionRepository = orderTransactionRepository;
    }

    @PostMapping("/send")
    public void send(@RequestBody Order order) {
        log.info("sender : " + order);
        jmsTemplate.convertAndSend("OrderTransactionQueue", order);

    }

    @DeleteMapping("/deleteOrder")
    public void deleteOrder(@RequestBody Order order) throws OrderNotFoundException {
        log.info("deleteOrder : " + order);
        orderTransactionRepository.delete(order);
    }

    @GetMapping("/getAllAddresses")
    public List<OrderDTO> getAllAddresses(@RequestParam(name = "address") String address,
                                          @RequestParam(name = "requestId") Long requestId) {
        log.info("getAllAddresses : params {} " + address + " " + requestId);
        List<Order> orders = orderTransactionRepository.findOrdersForAddressWherePriceIsMoreThanFifty(address);

        return orders.stream()
                .map(order -> orderConverter.overrideMethod(order, requestId))
                .peek(order -> log.info(order.toString() + " ***** "))
                .collect(Collectors.toList());
    }

    @GetMapping("/order/{orderName}")
    public List<Order> getOrdersByOrderName(@PathVariable String orderName) throws OrderNotFoundException {
        log.info("Get all orders with orderName {} ",orderName);
        return orderTransactionRepository.findByName(orderName)
                .orElseThrow(() -> new OrderNotFoundException("not found ",400));
    }


    @GetMapping("/getObject")
    public Order getObject() {
        return Order.builder()
                .address("address")
                .name("Rashad Javadov")
                .orderId(1234L)
                .build();
    }

}
