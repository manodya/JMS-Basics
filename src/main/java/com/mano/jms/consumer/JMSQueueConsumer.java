package com.mano.jms.consumer;

import com.google.gson.Gson;
import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by manodyas on 2/14/2018.
 */
// Asynchronous Consumer

public class JMSQueueConsumer implements MessageListener{
    private static Gson gson = new Gson();

    /**
     *
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
       JMSQueueConsumer consumer = new JMSQueueConsumer();
       consumer.start();
    }

    private  void start() throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(Constants.messageBrokerUrl);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue(Constants.outMessageQueueName);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
    }

    /**
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                String messageText = txtMsg.getText();
                System.out.println("##Test Message Received :" + txtMsg);
                System.out.println("Trader Name :" + txtMsg.getStringProperty("TraderName"));
                printHeaders(txtMsg);

            }else if(message instanceof ObjectMessage){
                ObjectMessage objectMessage = (ObjectMessage) message;
                System.out.println("#Object Message Received :" + objectMessage);
                System.out.println("Trader Name :" + objectMessage.getStringProperty("TraderName"));
                printHeaders(objectMessage);
            }
        } catch (JMSException e) {

        }
    }

    private static  void  printHeaders(Message message) throws JMSException {
        System.out.println("JMSDeliveryMode :" + message.getJMSDeliveryMode());
        System.out.println("JMSExpiration :" + message.getJMSExpiration());
        System.out.println("JMSPriority :" + message.getJMSPriority());

    }
}
