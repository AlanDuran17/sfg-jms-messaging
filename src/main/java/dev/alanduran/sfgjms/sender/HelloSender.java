package dev.alanduran.sfgjms.sender;

import dev.alanduran.sfgjms.config.JmsConfig;
import dev.alanduran.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

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
}
