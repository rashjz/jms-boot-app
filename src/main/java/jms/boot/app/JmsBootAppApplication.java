package jms.boot.app;

import jms.boot.app.domain.Order;
import jms.boot.app.repository.OrderTransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@EnableJpaRepositories
@SpringBootApplication
public class JmsBootAppApplication {

    @Bean
    CommandLineRunner runner(OrderTransactionRepository repository) {
        return arg -> repository.save(Order.builder()
                .address("my_address")
                .name("Rashad Javadov")
                .price(456)
                .orderId(1L)
                .build());
    }

    public static void main(String[] args) {
        SpringApplication.run(JmsBootAppApplication.class, args);
    }
}
