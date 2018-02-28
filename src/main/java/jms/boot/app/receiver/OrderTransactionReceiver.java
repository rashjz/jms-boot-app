package jms.boot.app.receiver;

import jms.boot.app.domain.Order;
import jms.boot.app.repository.OrderTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class OrderTransactionReceiver {

    @Autowired
    private OrderTransactionRepository transactionRepository;


    @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
    public void receiveMessage(@Payload final Order message) {
        log.info("Received <" + message.toString() + ">");
        transactionRepository.save(message);
    }
}