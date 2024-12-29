package pubsub.poll;

import pubsub.store.MessageStore;
import pubsub.Topics;
import pubsub.publish.MessagePublisher;
import pubsub.publish.impl.InMemoryMessagePublisher;

import java.util.List;

public class MessagePoller implements Runnable {

    MessagePublisher publisher;
    Topics topic;
    private volatile boolean running = true;

    public MessagePoller(Topics topic, MessagePublisher publisher) {
        this.topic = topic;
        this.publisher = publisher;
    }

    // Stop the MessagePoller
    public void stop() {
        running = false;
        System.out.println("MessagePoller stopped.");
    }


    @Override
    public void run() {

        MessageStore messageStore = MessageStore.getInstance();

        while (running) {

            try {
                String message = messageStore.getMessagesMap().get(topic).take();

                /*List<String> messages = messageStore.readAllMessages(topic);
                messages.add(message);*/

                publisher.publish(topic, message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
