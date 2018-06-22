package jms.boot.app.repository;

import jms.boot.app.domain.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

/**
 * OrderTransactionRepositoryTest test cases of {@link OrderTransactionRepository}
 **/
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderTransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderTransactionRepository orderTransactionRepository;


    @Test
    public void testSaveOrderEntity() {
        Order expectedOrder = entityManager.persist(expectedOrder());
        Order actualOrder = orderTransactionRepository.getOne(expectedOrder.getOrderId());

        Assert.assertNotNull(expectedOrder);
        Assert.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void getOneCheckExpectedResult() {
        Order order = orderTransactionRepository.getOne(1L);
        Assert.assertNotNull(order);
    }

    @Test(expected = Exception.class)
    public void getOrderExpectException() {
        when(entityManager.find(Order.class, 1L)).thenThrow(new Exception());
        orderTransactionRepository.getOne(1L);
    }

    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .price(33L)
                .build();
    }
} 
