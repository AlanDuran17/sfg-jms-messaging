package dev.alanduran.sfgjms.listener;

import dev.alanduran.sfgjms.config.JmsConfig;
import dev.alanduran.sfgjms.model.HelloWorldMessage;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.JMS_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {
        //System.out.println("Recieved message");

        //System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.JMS_SND_RCV_QUEUE)
    public void listenHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException {
        HelloWorldMessage reply = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Doody!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), reply);
    }
}
