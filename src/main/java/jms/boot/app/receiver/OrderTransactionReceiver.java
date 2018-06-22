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

    private final OrderTransactionRepository transactionRepository;

    @Autowired
    public OrderTransactionReceiver(OrderTransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @JmsListener(destination = "OrderTransactionQueue", containerFactory = "myFactory")
    public void receiveMessage(@Payload final Order message) {
        log.info("Received <" + message.toString() + ">");
        transactionRepository.save(message);
    }
}