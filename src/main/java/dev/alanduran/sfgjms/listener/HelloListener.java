package dev.alanduran.sfgjms.listener;

import dev.alanduran.sfgjms.config.JmsConfig;
import dev.alanduran.sfgjms.model.HelloWorldMessage;
import jakarta.jms.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HelloListener {

    @JmsListener(destination = JmsConfig.JMS_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) {
        System.out.println("Recieved message");

        System.out.println(helloWorldMessage);
    }
}
