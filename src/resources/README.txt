Current Architecture:

MessageProducer --> Puts the messages in the messageStore.
MessageStore    -->
       It is Singleton
       It has Map of the topic to linkedBlockingQueue

       Operations:

       addMessage : Producer puts the message to Message store
       ReadAllMessages : Reads the message from LinkedBlockingQueue given topic.

MessagePoller -->

    Different messagePoller per topic (Swim-laning)
    Runs continuously, waits till the message arrives in LinkedBlockingQueue
      --> Understand how wait works.

    As soon as message arrives, it's pushed to the Publisher.

MessagePublisher -->

   Takes the messages and publishes the message to the subscriber of the topic uses the topicRegistry.

TopicRegistry  -->
   It is Singleton
   It has Map of the topic to Subscribers

   addSubscriber    --> Operation called by Subscriber to subscribe to the topic
   removeSubscriber --> Operation called by Subscriber to unsubscribe from the topic

Subscriber -->

   OnMessage method to read the message from the topic.

Improvements:

a) Producer generates large number of messages (Just Random messages for now)
b) Apart from Singleton design pattern, which other design pattern can be used? (check Observer pattern once)
c) Subscriber being online/offline, in case any subscriber is offline while publishing the message add the message
   to the retry queue (read from main queue and add it to the retry queue)
d) RetryProcessor retries to publish the message to the Subscriber N number of times, after which moves the message to
   FailedQueue.

   --> retries after every 10 seconds, for maximum of 15 attempts.

e) Subscriber which can be made online/offline using in memory database flag or randmoly after few minutes.
   --> for now, let's do hardcoding of this data.
       subscriber 1 --> Goes offline after 20 seconds, stays offline for 1 minute and comes back again
                        and hence should receive all the messages.
       subscriber 2 --> Remains offline forever.
       subscriber 3 --> Remains online forever.

f) Rate limiting feature implementation (using some library)

    --> explore how subscriber can stop consuming the messages once it reaches the limit.
g) Currently, as soon as messages comes in the LinkedBlockingQueue it is published, need to do bulk
   read and publish

   --> explore different API's of the LinkedBlockingQueue
h) Can I use any Java generics in this implementation?

