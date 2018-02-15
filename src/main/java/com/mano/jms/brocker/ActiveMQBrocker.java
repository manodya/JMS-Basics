package com.mano.jms.brocker;

import org.apache.activemq.broker.BrokerService;

/**
 * Created by manodyas on 2/14/2018.
 */
public class ActiveMQBrocker {
    public static void main(String[] args) throws Exception {
        startBroker();
    }

    private static void startBroker() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();

    }
}
