package com.mano.jms.producer;

import com.mano.jms.Constants;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.DecimalFormat;

/**
 * Created by manodyas on 2/15/2018.
 */
public class JMSPublisher {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.messageBrokerUrl);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(Constants.tradeEventTopic);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        MessageProducer publisher = session.createProducer(topic);
        String price = decimalFormat.format(95.0 + Math.random());
        TextMessage message = session.createTextMessage("AAPL :" + price);
        publisher.send(message);
        connection.close();

    }
}
