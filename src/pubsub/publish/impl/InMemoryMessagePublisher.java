package pubsub.publish.impl;

import pubsub.TopicRegistry;
import pubsub.Topics;
import pubsub.model.RetryMessageData;
import pubsub.publish.MessagePublisher;
import pubsub.store.RetryMessageStore;
import pubsub.subscribe.Subscriber;

import java.util.List;

import static pubsub.utility.CommonUtilities.isSubscriberOnline;

public class InMemoryMessagePublisher implements MessagePublisher {


    RetryMessageStore retryMessageStore = RetryMessageStore.getInstance();

    @Override
    public void publish(Topics topic, String message) {

        if(message != null && !message.isEmpty()) {

            TopicRegistry registry = TopicRegistry.getInstance();

            List<Subscriber> subscriberList = registry.getTopicsMap().get(topic);

            if(subscriberList != null && !subscriberList.isEmpty()) {


                for (Subscriber subscriber : subscriberList) {

                    if(isSubscriberOnline.test(subscriber)) {
                        subscriber.onMessage(topic, message);
                    } else {
                        retryMessageStore.putMessages(new RetryMessageData(subscriber,topic,message));
                    }
                }

            } else {
                System.out.println("No subscribers found for topic: " + topic);
            }

        }
    }

    @Override
    public void publish(Topics topic, List<String> message) {


    }

    @Override
    public void publish(RetryMessageData retryMessageData) {

        Subscriber subscriber = retryMessageData.getSubscriber();

        if(isSubscriberOnline.test(subscriber)) {
            subscriber.onMessage(retryMessageData.getTopics(), retryMessageData.getMessage());
            retryMessageData.setRetrySuccessful(true);
            retryMessageStore.removeMessage(retryMessageData);
        } else {
            retryMessageData.setRetryCount(retryMessageData.getRetryCount()+1);
            retryMessageStore.putMessages(retryMessageData);
        }
    }
}
