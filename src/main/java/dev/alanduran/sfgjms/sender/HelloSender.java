package dev.alanduran.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alanduran.sfgjms.config.JmsConfig;
import dev.alanduran.sfgjms.model.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        System.out.println("Sending a message");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hey, how you doin'?")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.JMS_QUEUE, message);

        System.out.println("Message sent");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {

        System.out.println("Sending a message");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Howdy!")
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.JMS_SND_RCV_QUEUE, session -> {
            Message helloMessage;
            try {
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type", "dev.alanduran.sfgjms.model.HelloWorldMessage");
                System.out.println("Sending Hello");
            } catch (JsonProcessingException e) {
                throw new JMSException("Something went wrong while creating the message");
            }
            return helloMessage;
        });

        System.out.println(receivedMessage.getBody(String.class));
    }
}
