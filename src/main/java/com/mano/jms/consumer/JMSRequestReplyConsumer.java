package com.mano.jms.consumer;

import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/15/2018.
 */
public class JMSRequestReplyConsumer implements MessageListener {
    private static ActiveMQConnectionFactory activeMQConnectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination queue;
    private static MessageConsumer consumer;
    private static MessageProducer producer;

    public static void main(String[] args) throws JMSException {
        activeMQConnectionFactory = new ActiveMQConnectionFactory(Constants.messageBrokerUrl);
        connection = activeMQConnectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(Constants.outMessageQueueName);
        consumer = session.createConsumer(queue);
        JMSRequestReplyConsumer jmsRequestReplyConsumer = new JMSRequestReplyConsumer();
        consumer.setMessageListener(jmsRequestReplyConsumer);
    }


    @Override
    public void onMessage(Message message) {
        System.out.println("##" + message);
        System.out.println("Processing Trade");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TextMessage response = session.createTextMessage("EQ-12345");
            response.setJMSCorrelationID(message.getJMSMessageID());
            producer = session.createProducer(message.getJMSReplyTo());
            producer.send(response);
        } catch (JMSException e) {
            e.printStackTrace();
        }



    }
}
