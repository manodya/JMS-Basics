package com.mano.jms.consumer;

import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/15/2018.
 */

/**
 * This is a Non-durable Subscriber
 */
public class JMSSubscriber implements MessageListener {
    public static void main(String[] args) throws JMSException {
        TopicConnection connection = new ActiveMQConnectionFactory(Constants.messageBrokerUrl).createTopicConnection();
       connection.start();
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(Constants.tradeEventTopic);
        TopicSubscriber subscriber = session.createSubscriber(topic);
        subscriber.setMessageListener(new JMSSubscriber());

    }


    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("Message Received :" + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
