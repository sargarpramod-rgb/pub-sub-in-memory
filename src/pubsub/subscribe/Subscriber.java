package pubsub.subscribe;

import pubsub.Topics;

import java.util.List;

public interface Subscriber {

    public void subscribe(Topics topic);

    public void unsubscribe(Topics topic);

    public void onMessage(Topics topic, String message);

    public void onMessage(Topics topic, List<String> message);

    public SubscriberStatus getSubscriberStatus();
    public void setSubscriberStatus(SubscriberStatus status);

}
