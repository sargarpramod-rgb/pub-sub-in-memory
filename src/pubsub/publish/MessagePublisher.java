package pubsub.publish;

import pubsub.Topics;
import pubsub.model.RetryMessageData;

import java.util.List;

public interface MessagePublisher {

    public void publish(Topics topic,String message);

    public void publish(Topics topic, List<String> message);

    public void publish(RetryMessageData retryMessageData);

}
