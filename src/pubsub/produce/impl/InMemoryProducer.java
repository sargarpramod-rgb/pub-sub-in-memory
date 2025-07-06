package pubsub.produce.impl;

import pubsub.store.MessageStore;
import pubsub.Topics;
import pubsub.produce.Producer;

import java.util.Scanner;

public class InMemoryProducer extends MessageStore implements Producer, Runnable {

    MessageStore messageStore;
    private volatile boolean running = true;

    public InMemoryProducer() {
        this.messageStore = MessageStore.getInstance();
    }

    @Override
    public void produce(Topics topic, String message) {

        messageStore.addMessage(topic, message);
    }

    // Stop the producer
    public void stop() {
        running = false;
        System.out.println("Producer stopped.");
    }


    @Override
    public void run() {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("1.Enter messages (type 'exit' to stop):");
            System.out.println("2.Enter topic followed by message in this format: topic#message");
            while (running) {
                String input = scanner.nextLine();

                // Stop if the user types "exit"
                if ("exit".equalsIgnoreCase(input.trim())) {
                    stop();
                    break;
                }

                String[] split = input.split("#");

                produce(Topics.valueOf(split[0]),split[1]);
                System.out.println("Message added to topic '" + split[0] + "'");
            }
        }
    }
}
