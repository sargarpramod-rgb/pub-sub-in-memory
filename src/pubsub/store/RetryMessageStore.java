package pubsub.store;

import pubsub.model.RetryMessageData;
import pubsub.subscribe.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RetryMessageStore {

    // Needs to store the messages in the format
    // Subscriber, List<messages>

    private static RetryMessageStore instance = new RetryMessageStore();
    private List<RetryMessageData> retryMessageDataList = new CopyOnWriteArrayList<>();

    private RetryMessageStore() {}

    public static RetryMessageStore getInstance() {
        return instance;
    }

    public void putMessages(RetryMessageData retryMessageData) {

        retryMessageDataList.add(retryMessageData);
    }

    public void removeMessage(RetryMessageData retryMessageData) {

        retryMessageDataList.remove(retryMessageData);
    }

    public List<RetryMessageData> getMessages() {

        return retryMessageDataList;
    }
}
