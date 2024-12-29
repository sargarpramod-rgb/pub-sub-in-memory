package pubsub.model;

import pubsub.Topics;
import pubsub.subscribe.Subscriber;


public class RetryMessageData {

    Subscriber subscriber;
    Topics topics;
    String message;
    int retryCount = 0;
    int maxRetryCount = 15;


    boolean retrySuccessful = false;

    public RetryMessageData(Subscriber subscriber, Topics topics, String message) {
        this.subscriber = subscriber;
        this.topics = topics;
        this.message = message;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public boolean isRetrySuccessful() {
        return retrySuccessful;
    }

    public void setRetrySuccessful(boolean retrySuccessful) {
        this.retrySuccessful = retrySuccessful;
    }

}
