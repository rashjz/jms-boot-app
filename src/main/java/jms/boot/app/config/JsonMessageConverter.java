package jms.boot.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;


@Component
public class JsonMessageConverter implements MessageConverter {

    private final ObjectMapper mapper;

    @Autowired
    public JsonMessageConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    public javax.jms.Message toMessage(Object object, Session session) throws JMSException {
        String json;

        try {
            json = mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new MessageConversionException("Message cannot be parsed. ", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(json);

        return message;
    }

    @Override
    public Object fromMessage(javax.jms.Message message) throws JMSException {
        return ((TextMessage) message).getText();
    }
}