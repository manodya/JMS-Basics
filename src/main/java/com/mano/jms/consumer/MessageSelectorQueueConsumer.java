package com.mano.jms.consumer;

import com.google.gson.Gson;
import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/15/2018.
 */
public class MessageSelectorQueueConsumer implements MessageListener {
    private static ActiveMQConnectionFactory activeMQConnectionFactory;
    private static Connection connection;
    private static Session session;
    private static Destination queue;
    private static MessageConsumer messageConsumer;
    private static TextMessage textMessage;
    private static ObjectMessage objectMessage;
    private static MapMessage mapMessage;
    private static Gson gson = new Gson();


    public static void main(String[] args) throws JMSException {
        start();
    }

    private static void start() throws JMSException {
      connection = new ActiveMQConnectionFactory(Constants.messageBrokerUrl).createConnection();
      connection.start();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      queue = session.createQueue(Constants.outMessageQueueName);
      messageConsumer = session.createConsumer(queue, "Stage = 'Open'");
      messageConsumer.setMessageListener(new MessageSelectorQueueConsumer());
        System.out.println("Waiting for Messages , Filter :" + messageConsumer.getMessageSelector() );
    }

    @Override
    public void onMessage(Message msg) {
       textMessage  = (TextMessage) msg;
        try {
            System.out.println(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
