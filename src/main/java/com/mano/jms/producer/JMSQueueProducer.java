package com.mano.jms.producer;

import com.google.gson.Gson;
import com.mano.jms.Constants;
import com.mano.jms.bo.Order;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.leveldb.replicated.MasterLevelDBStore;

import javax.jms.*;

/**
 * Created by manodyas on 2/14/2018.
 */

/**
 * Standard JMS Header Properties
 * We can configure only JMSCorrelationID , JMSType and JMSReplyTo only in the Message Level and other header parameters in the
 * below will be overridden  by default values
 * JMSDeliveryMode = 2, JMSExpiration - 0, JMSPriority -, JMSDeliveryTIme - 0 these values
 * configured in the MessageProducer Level
*/
public class JMSQueueProducer {
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


        activeMQConnectionFactory = new ActiveMQConnectionFactory(Constants.messageBrokerUrl);
        connection = activeMQConnectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(Constants.outMessageQueueName);
        producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.setPriority(3);
        sendTextMessage();
        sendObjectMessage();
        connection.close();
    }

    /**
     *
     * @throws JMSException
     */
    private static void sendTextMessage() throws JMSException {

        textMessage = session.createTextMessage("BUY APPL 100 Shares");
        textMessage.setJMSCorrelationID(String.valueOf(System.currentTimeMillis()));
        textMessage.setStringProperty("TraderName", "Mano");
        textMessage.setJMSType("TXT");
        textMessage.setJMSReplyTo(session.createQueue(Constants.inMessageQueueName));
        System.out.println("#Test Message" + textMessage);
        printHeaders(textMessage);
        producer.send(textMessage);
    }

    private static  void sendObjectMessage() throws JMSException {
        objectMessage = session.createObjectMessage();
        Order order = new Order("BUY", 150, "APPL");
        objectMessage.setObject(order);
        objectMessage.setStringProperty("TraderName", "Mano");
        objectMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
        objectMessage.setJMSExpiration(5000);
        objectMessage.setJMSPriority(1);
       // objectMessage.setJMSCorrelationID(objectMessage.getJMSMessageID());
        System.out.println("#Object Message :" + objectMessage);
        printHeaders(objectMessage);
        producer.send(objectMessage);

    }

    /**
     *
     * @param message
     * @throws JMSException
     */
    private static  void  printHeaders(Message message) throws JMSException {
        System.out.println("JMSDeliveryMode :" + message.getJMSDeliveryMode());
        System.out.println("JMSExpiration :" + message.getJMSExpiration());
        System.out.println("JMSPriority :" + message.getJMSPriority());

    }
}
