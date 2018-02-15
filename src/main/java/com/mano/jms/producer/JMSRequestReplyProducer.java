package com.mano.jms.producer;

import com.google.gson.Gson;
import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/15/2018.
 */

/**
 * http://www.wmrichards.com/emvideocode.html
 */
public class JMSRequestReplyProducer {
    private static ActiveMQConnectionFactory activeMQConnectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination reqQueue;
    private static Destination resQueue;
    private static MessageProducer producer;
    private static MessageConsumer consumer;
    private static TextMessage textMessage;
    private static Gson gson = new Gson();

    public static void main(String[] args) throws JMSException {

        activeMQConnectionFactory = new ActiveMQConnectionFactory(Constants.messageBrokerUrl);
        connection = activeMQConnectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        reqQueue = session.createQueue(Constants.outMessageQueueName);
        resQueue = session.createQueue(Constants.inMessageQueueName);
        producer = session.createProducer(reqQueue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setPriority(4);

        TextMessage message = session.createTextMessage("BUY AAPL 1000 SHARES");
        message.setJMSReplyTo(resQueue);
        producer.send(message);

        String filter = "JMSCorrelationID = '" + message.getJMSMessageID() + "'";
        consumer = session.createConsumer(resQueue, filter);
        TextMessage textMessage = (TextMessage) consumer.receive();
        System.out.println("Confirmation :" + textMessage.getText());
        connection.close();



    }
}
