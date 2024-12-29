package pubsub.store;

import pubsub.Topics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageStore {

    private static MessageStore instance = new MessageStore();

    private Map<Topics, LinkedBlockingQueue<String>> messagesMap = new ConcurrentHashMap<Topics, LinkedBlockingQueue<String>>() {
        {
            put(Topics.POLITICAL_NEWS,new LinkedBlockingQueue<String>());
            put(Topics.EDTECH_NEWS,new LinkedBlockingQueue<String>());
            put(Topics.FINANCIAL_NEWS,new LinkedBlockingQueue<String>());
        }
    };

    private MessageStore() {}
    public static MessageStore getInstance() {return instance;}

    public Map<Topics, LinkedBlockingQueue<String>> getMessagesMap() {
        return Collections.unmodifiableMap(messagesMap);
    }

    public void addMessage(Topics topic, String message) {
        try {
            messagesMap.get(topic).put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> readAllMessages(Topics topic) {
        List<String> messages = new ArrayList<>();

        LinkedBlockingQueue<String> linkedBlockingQueue = messagesMap.get(topic);

        if(linkedBlockingQueue != null) {
            linkedBlockingQueue.drainTo(messages);
        }

        return messages;
    }
}
