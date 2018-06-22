package jms.boot.app.receiver;

import jms.boot.app.JmsBootAppApplication;
import jms.boot.app.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JmsBootAppApplication.class})
public class OrderTransactionReceiverTest {

    private static final String DESTINATION = "OrderTransactionQueue";


    @MockBean
    private JmsTemplate jmsTemplate;

    @Test
    public void test() {
        this.jmsTemplate.convertAndSend(DESTINATION, expectedOrder());
        assertThat(this.jmsTemplate.receiveAndConvert(DESTINATION)).isEqualTo(expectedOrder());
    }


    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .orderId(1234L)
                .build();
    }
}
