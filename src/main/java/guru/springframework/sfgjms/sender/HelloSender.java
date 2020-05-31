package guru.springframework.sfgjms.sender;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HelloSender {

    // inject the jmsTemplate from spring to send messages to ActiveMQ
    private final JmsTemplate jmsTemplate;

    public HelloSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Scheduled(fixedRate = 2000) // automate 2sec delay
    public void sendMessage(){

        System.out.println("Im Sending a message");

        // create the message
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        // send the message to ActiveMQ
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        System.out.println("Message Sent!");

    }
}
