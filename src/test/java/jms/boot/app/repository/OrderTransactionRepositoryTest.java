package jms.boot.app.repository;

import jms.boot.app.JmsBootAppApplication;
import jms.boot.app.domain.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {JmsBootAppApplication.class})
public class OrderTransactionRepositoryTest {

    @MockBean
    private TestEntityManager entityManager;

    @Autowired
    private OrderTransactionRepository orderTransactionRepository;


    @Test
    public void testSaveOrderEntity() {
        when(entityManager.persist(expectedOrder())).thenReturn(Order.builder()
                .address("address")
                .name("name")
                .orderId(1L)
                .build());
        Order order1 = orderTransactionRepository.save(expectedOrder());
        Assert.assertTrue(order1.getOrderId() == 1L);
    }

    @Test
    public void testGetOrder() {
        when(entityManager.find(Order.class, 1L)).thenReturn(expectedOrder());
        orderTransactionRepository.getOne(1L);
    }

    @Test(expected = Exception.class)
    public void testGetOrderExpectException() {
        when(entityManager.find(Order.class, 1L)).thenThrow(new Exception());
        orderTransactionRepository.getOne(1L);
    }

    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .orderId(1234L)
                .build();
    }
} 
