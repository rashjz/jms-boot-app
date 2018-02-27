package jms.boot.app.controller;

import jms.boot.app.domain.Order;
import jms.boot.app.repository.OrderTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Created by Rashad on 2/26/2018.
 */
@Slf4j
@Component
public class OrderTransactionReceiver {

    @Autowired
    private OrderTransactionRepository transactionRepository;


    @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
    public void receiveMessage( @Payload final Message<Order> message) {
        log.info("Received <" + message.getPayload() + ">");
        transactionRepository.save( message.getPayload());
    }
}