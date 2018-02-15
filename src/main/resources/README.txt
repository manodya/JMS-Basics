Point to Point Model
1. Fire and Forget
2. Request/Response model
   QueueRequester Mechanism of Blocking Mechanism
Pub/Sub Model
* When you don't need a reply from the consumer
* When you don't care who is receiving the message
* When you need to deliver the same message to multiple consumers

Topic is virtual and if there is no any listener or subscriber message will be lost.
Subscribers:
1. Non-durable subscribers - Lost the messages while they are down
2. Durable Subscribers - inactive subscribers will receive stored messages when they become active again
3. Non-durable shared subscriber - While keeping the non-durable behavior only one subscriber in the group will receive the message (kind of load balancing)
4. Durable shared subscriber

####################################
Message Selectors
- We can filter only message Header properties