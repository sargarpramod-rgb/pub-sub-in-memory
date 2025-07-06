package pubsub.produce;

import pubsub.Topics;

public interface Producer {

    public void produce(Topics topic, String message);
}
