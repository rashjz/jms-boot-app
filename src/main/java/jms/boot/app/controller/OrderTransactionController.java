package jms.boot.app.controller;

import jms.boot.app.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/transaction")
public class OrderTransactionController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/send")
    public void send(@RequestBody Order order) {
        log.info("sender : " + order);
        jmsTemplate.convertAndSend("OrderTransactionQueue", order);

    }

    @GetMapping("/getObject")
    public
    @ResponseBody
    Order getObject() {
        return Order.builder()
                .address("S Asgarova 103")
                .name("Rashad Javadov")
                .orderId(Long.valueOf(1234))
                .build();
    }

}