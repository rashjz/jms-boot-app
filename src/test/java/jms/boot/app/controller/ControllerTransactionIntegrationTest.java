package jms.boot.app.controller;

import jms.boot.app.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControllerTransactionIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testRestSendMethod() {

        ResponseEntity<Object> responseEntity = restTemplate.postForEntity("/transaction/send", expectedOrder(), Object.class);

        Object responseEntityBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntityBody);
    }

    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .orderId(null)
                .build();
    }
}
