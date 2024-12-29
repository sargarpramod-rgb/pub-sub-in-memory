package pubsub.subscribe.impl;

import pubsub.TopicRegistry;
import pubsub.Topics;
import pubsub.subscribe.Subscriber;
import pubsub.subscribe.SubscriberStatus;

import java.util.List;

public class InMemorySubscriber implements Subscriber {

    private SubscriberStatus subscriberStatus;
    String name;
    TopicRegistry topicRegistry;

    public InMemorySubscriber(String name, SubscriberStatus subscriberStatus) {
        this.name=name;
        this.subscriberStatus=subscriberStatus;
        this.topicRegistry = TopicRegistry.getInstance();
    }

    @Override
    public void subscribe(Topics topic) {
        topicRegistry.addSubscriber(topic,this);
    }

    @Override
    public void unsubscribe(Topics topic) {
        topicRegistry.removeSubscriber(topic,this);
    }


    @Override
    public void onMessage(Topics topic, String message) {
        System.out.println(display(topic,message));
    }

    @Override
    public void onMessage(Topics topic, List<String> messages) {
        messages.forEach(message -> System.out.println(display(topic,message)));
    }


    public String display(Topics topic, String message) {
        return name+": " + topic.name() + ": " + message;
    }

    public SubscriberStatus getSubscriberStatus() {
        return subscriberStatus;
    }

    public void setSubscriberStatus(SubscriberStatus status) {
        this.subscriberStatus = status;
    }
}
