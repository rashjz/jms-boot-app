package jms.boot.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jms.boot.app.JmsBootAppApplication;
import jms.boot.app.domain.Order;
import jms.boot.app.repository.OrderTransactionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OrderTransactionControllerTest test cases of {@link OrderTransactionController}
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JmsBootAppApplication.class})
@WebAppConfiguration
public class OrderTransactionControllerTest {

    private String DESTINATION = "\"OrderTransactionQueue\"";

    @Mock
    private OrderTransactionRepository repository;

    @InjectMocks
    private OrderTransactionController orderTransactionController;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderTransactionController).build();

    }

    @Test(expected = Exception.class)
    public void testSendMethod() throws Exception {
        doThrow(new Exception()).when(orderTransactionController).deleteOrder(any());
        mockMvc.perform(post("/transaction/deleteOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isOk());
    }

    @Test
    public void testSuccessDeleteMethod() throws Exception {
        doNothing().when(repository).delete(expectedOrder());
        Assert.assertNotNull(objectMapper.writeValueAsString(expectedOrder()));


        mockMvc.perform(delete("/transaction/deleteOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status()
                        .isOk());
        verify(repository, times(1)).delete(expectedOrder());
    }


    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .orderId(1234L)
                .build();
    }
}