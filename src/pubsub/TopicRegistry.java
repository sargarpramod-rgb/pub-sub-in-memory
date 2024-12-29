package pubsub;

import pubsub.subscribe.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class TopicRegistry {

    private static TopicRegistry instance = new TopicRegistry();

    private Map<Topics,List<Subscriber>> topicsMap = new ConcurrentHashMap<>();

    private TopicRegistry() {}
    public static TopicRegistry getInstance() {return instance;}

    public Map<Topics, List<Subscriber>> getTopicsMap() {
        return Collections.unmodifiableMap(topicsMap);
    }

    // Add a subscriber to a topic
    public void addSubscriber(Topics topic, Subscriber subscriber) {
        topicsMap.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(subscriber);
    }

    // Remove a subscriber from a topic
    public void removeSubscriber(Topics topic, Subscriber subscriber) {
        List<Subscriber> subscribers = topicsMap.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
            if (subscribers.isEmpty()) {
                topicsMap.remove(topic);
            }
        }
    }

}
