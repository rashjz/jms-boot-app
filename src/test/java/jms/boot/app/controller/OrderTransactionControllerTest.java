package jms.boot.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jms.boot.app.domain.Order;
import jms.boot.app.repository.OrderTransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OrderTransactionControllerTest test cases of {@link OrderTransactionController}
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderTransactionControllerTest {

    private String DESTINATION = "OrderTransactionQueue";

    @MockBean
    private OrderTransactionRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JmsTemplate jmsTemplate;
 
    @Test
    public void testSuccessDeleteMethod() throws Exception {
        doNothing().when(repository).delete(expectedOrder());
        Assert.assertNotNull(objectMapper.writeValueAsString(expectedOrder()));


        mockMvc.perform(delete("/transaction/deleteOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isOk());
        verify(repository, times(1)).delete(expectedOrder());
    }

    @Test
    public void sendTest() throws Exception {
//        given(createClientServiceMock.createClient("Foo")).willReturn(new Client("Foo"));

        doNothing().when(jmsTemplate).convertAndSend(DESTINATION, expectedOrder());

        mockMvc.perform(post("/transaction/send")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isOk());
        verify(jmsTemplate, times(1)).convertAndSend(DESTINATION, expectedOrder());


    }

    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .orderId(1234L)
                .build();
    }
}
