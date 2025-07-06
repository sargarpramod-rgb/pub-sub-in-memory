package pubsub.poll;

import pubsub.model.RetryMessageData;
import pubsub.publish.MessagePublisher;
import pubsub.store.RetryMessageStore;

import java.util.List;
import java.util.TimerTask;

public class RetryMessageProcessor extends TimerTask {

    MessagePublisher publisher;
    RetryMessageStore retryMessageStore = RetryMessageStore.getInstance();

    @Override
    public void run() {

        List<RetryMessageData> retryMessagesList = retryMessageStore.getMessages();

        retryMessagesList.stream().filter(retryMessageData ->
                        retryMessageData.getRetryCount() > retryMessageData.getMaxRetryCount()).
                forEach(retryMessageData -> publisher.publish(retryMessageData));

    }
}
