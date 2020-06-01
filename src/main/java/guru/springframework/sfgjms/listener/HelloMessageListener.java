package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    public HelloMessageListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

//    Listen to a message , dont reply
    //    @Payload - tell spring to deserielise the message
//    @JmsListener(destination = JmsConfig.MY_QUEUE)// register as a jmsListener and register to the queue
//    public void listen(@Payload HelloWorldMessage helloWorldMessage,
//                       @Headers MessageHeaders headers, Message message){
//        System.out.println("I got a message !!!");
//
//        System.out.println(helloWorldMessage);
//    }


//    Listen and respond to a message
    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)// register as a jmsListener and register to the queue
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message jmsMessage,
                               org.springframework.messaging.Message springMessage) throws JMSException {

        // create the message to reply
        HelloWorldMessage payloadMsg = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("World !!")
                .build();

        // using the spring version message api
        //jmsTemplate.convertAndSend((Destination)springMessage.getHeaders().get("jms_replyTo"), "Got it!");

        // using the native jms message api
        jmsTemplate.convertAndSend(jmsMessage.getJMSReplyTo(), payloadMsg);
    }

}
