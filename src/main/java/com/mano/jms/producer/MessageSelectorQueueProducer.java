package com.mano.jms.producer;

import com.google.gson.Gson;
import com.mano.jms.Constants;
import junit.framework.Test;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/15/2018.
 */
public class MessageSelectorQueueProducer {
    private static ActiveMQConnectionFactory activeMQConnectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination queue;
    private static MessageProducer producer;
    private static TextMessage textMessage;
    private static ObjectMessage objectMessage;
    private static MapMessage mapMessage;
    private static Gson gson = new Gson();

    public static void main(String[] args) throws JMSException {
        connection = new ActiveMQConnectionFactory(Constants.messageBrokerUrl).createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(Constants.outMessageQueueName);
        producer = session.createProducer(queue);
        textMessage = session.createTextMessage("BUY AAPL 1000 Shares");
        textMessage.setStringProperty("Stage", "Open");
        producer.send(textMessage);

        System.out.println("Message Sent , Message ID:" + textMessage.getJMSMessageID());
        connection.close();

    }
}
