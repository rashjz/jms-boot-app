package jms.boot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@EnableJpaRepositories
@SpringBootApplication
public class JmsBootAppApplication {

//    @Bean
//    CommandLineRunner runner(OrderTransactionRepository repository) {
//        return arg -> {
//            repository.save(Order.builder().address("S Asgarova 103").name("Rashad Javadov").orderId(Long.valueOf(1234)).build());
//        };
//    }

    public static void main(String[] args) {
        SpringApplication.run(JmsBootAppApplication.class, args);
    }
}
