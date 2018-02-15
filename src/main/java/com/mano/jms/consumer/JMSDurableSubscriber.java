package com.mano.jms.consumer;

import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/15/2018.
 */
public class JMSDurableSubscriber implements MessageListener {
    public static void main(String[] args) throws JMSException {
        TopicConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616?jms.clientID=client:1").createTopicConnection();
        connection.start();
        TopicSession topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = topicSession.createTopic(Constants.tradeEventTopic);
        TopicSubscriber subscriber = topicSession.createDurableSubscriber(topic, "sub:123");
        subscriber.setMessageListener(new JMSDurableSubscriber());


    }


    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
