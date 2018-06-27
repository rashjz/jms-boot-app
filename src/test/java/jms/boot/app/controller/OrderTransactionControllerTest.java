package jms.boot.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jms.boot.app.domain.Order;
import jms.boot.app.exception.OrderNotFoundException;
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

import java.nio.charset.Charset;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OrderTransactionControllerTest test cases of {@link OrderTransactionController}
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderTransactionControllerTest {
    private static final String ORDER_NOT_FOUND_MESSAGE = "Requested order not found";

    private static final String ILLEGAL_ARG_EXP_MSG = "This should be application specific";
    private static final String DESTINATION = "OrderTransactionQueue";
    private static final String ORDER_NAME = "TestOrderName";
    private static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

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
                .contentType(contentType)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isOk());
        verify(repository, times(1)).delete(expectedOrder());
    }

    @Test
    public void testDeleteMethodThrowExceptionNotFound() throws Exception {
        doThrow(new OrderNotFoundException("No specific Order", 404)).when(repository).delete(expectedOrder());

        mockMvc.perform(delete("/transaction/deleteOrder")
                .contentType(contentType)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void sendTestCheckSuccessResult() throws Exception {
//        given(createClientServiceMock.createClient("Foo")).willReturn(new Client("Foo"));

        doNothing().when(jmsTemplate).convertAndSend(DESTINATION, expectedOrder());

        mockMvc.perform(post("/transaction/send")
                .contentType(contentType)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isOk());
        verify(jmsTemplate, times(1)).convertAndSend(DESTINATION, expectedOrder());


    }

    @Test
    public void testGetOrdersByOrderNameThrowIllegalArgumentException() throws Exception {

        doThrow(new IllegalArgumentException()).when(repository).findByName(ORDER_NAME);

        mockMvc.perform(get("/transaction/order/" + ORDER_NAME)
                .contentType(contentType)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isConflict())
                .andExpect(content().string(ILLEGAL_ARG_EXP_MSG));
    }

    @Test
    public void testGetOrdersByOrderNameThrowOrderNotFoundException() throws Exception {

        doThrow(new OrderNotFoundException(ORDER_NOT_FOUND_MESSAGE, -1)).when(repository).findByName(ORDER_NAME);

        mockMvc.perform(get("/transaction/order/" + ORDER_NAME)
                .contentType(contentType)
                .accept(MediaType.ALL)
                .content(objectMapper.writeValueAsString(expectedOrder())))
                .andExpect(status().isConflict())
                .andExpect(content().string(ORDER_NOT_FOUND_MESSAGE));
    }

    public Order expectedOrder() {
        return Order.builder()
                .address("address")
                .name("name")
                .orderId(1234L)
                .build();
    }
}
