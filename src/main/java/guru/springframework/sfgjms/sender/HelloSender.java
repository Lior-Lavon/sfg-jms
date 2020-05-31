package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
public class HelloSender {

    // inject the jmsTemplate from spring to send messages to ActiveMQ
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public HelloSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

//    @Scheduled(fixedRate = 4000) // automate 2sec delay
    public void sendMessage(){

        // create the message
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        // send the message to ActiveMQ
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 4000) // automate 2sec delay
    public void sendAndReceiveMessage() throws JMSException {

        // create message to send
        HelloWorldMessage messageToSend = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        // send the message and listen for reply on ActiveMQ
        Message receiveMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(messageToSend));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

                    System.out.println("Sending hello");

                    return helloMessage;

                } catch (JsonProcessingException e) {
                    // e.printStackTrace();
                    throw new JMSException("boom");
                }
            }
        });

        // print the received output
        System.out.println(receiveMsg.getBody(String.class));
    }

}
