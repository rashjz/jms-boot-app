package jms.boot.app.controller;

import jms.boot.app.domain.Order;
import jms.boot.app.repository.OrderTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Rashad on 2/26/2018.
 */
@Slf4j
@RestController
@RequestMapping("/transaction")
public class OrderTransactionController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/send")
    public void send(@RequestBody Order transaction) {
        log.info("sender : " + transaction);
        // Post message to the message queue named "OrderTransactionQueue"

        jmsTemplate.convertAndSend("OrderTransactionQueue", transaction);
    }

    @GetMapping("/getObject")
    public @ResponseBody Order getObject() {
        return Order.builder()
                .address("S Asgarova 103")
                .name("Rashad Javadov")
                .orderId(Long.valueOf(1234))
                .build();
    }

}