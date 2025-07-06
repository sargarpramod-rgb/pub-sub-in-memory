package pubsub;

import pubsub.poll.MessagePoller;
import pubsub.poll.SubScriberStatusChangePoller;
import pubsub.produce.impl.InMemoryProducer;
import pubsub.publish.MessagePublisher;
import pubsub.publish.impl.InMemoryMessagePublisher;
import pubsub.subscribe.SubscriberStatus;
import pubsub.subscribe.impl.InMemorySubscriber;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PubSubExample {

    public static void main(String[] args) throws InterruptedException {

        MessagePublisher messagePublisher = new InMemoryMessagePublisher();

        // TODO : create factory which creates the subscriber and adds it to the subscriberStatusChangePoller
        InMemorySubscriber subscriber1 =
                new InMemorySubscriber("Sub1", SubscriberStatus.ONLINE);
        subscriber1.subscribe(Topics.POLITICAL_NEWS);

        InMemorySubscriber subscriber2 = new InMemorySubscriber("Sub2",SubscriberStatus.ONLINE);
        subscriber2.subscribe(Topics.POLITICAL_NEWS);
        subscriber2.subscribe(Topics.EDTECH_NEWS);

        InMemorySubscriber subscriber3 = new InMemorySubscriber("Sub3",SubscriberStatus.OFFLINE);
        subscriber2.subscribe(Topics.POLITICAL_NEWS);
        subscriber2.subscribe(Topics.EDTECH_NEWS);

       ExecutorService executorServiceSubScriber = Executors.newFixedThreadPool(3);
        executorServiceSubScriber.submit(new SubScriberStatusChangePoller(subscriber1, true,
                Duration.ofSeconds(10), Duration.ofSeconds(60)));
        executorServiceSubScriber.submit(new SubScriberStatusChangePoller(subscriber2, false,
                Duration.ZERO,  Duration.ZERO));
        executorServiceSubScriber.submit(new SubScriberStatusChangePoller(subscriber3, false,
                Duration.ZERO,  Duration.ZERO));

        /*producer.produce(Topics.POLITICAL_NEWS,"My first message");
        producer.produce(Topics.POLITICAL_NEWS,"My second message");
        producer.produce(Topics.POLITICAL_NEWS,"My third message");*/

        MessagePoller politicsNewsPoller = new MessagePoller(Topics.POLITICAL_NEWS,messagePublisher);
        MessagePoller edTechNewsPoller = new MessagePoller(Topics.EDTECH_NEWS,messagePublisher);
        MessagePoller financialNewsPoller = new MessagePoller(Topics.FINANCIAL_NEWS,messagePublisher);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Thread(politicsNewsPoller));
        executorService.submit(new Thread(edTechNewsPoller));
        executorService.submit(new Thread(financialNewsPoller));


        InMemoryProducer producer = new InMemoryProducer();
        Thread producerThread = new Thread(producer);
        producerThread.start();

        producerThread.join();
        executorService.shutdown();
        executorServiceSubScriber.shutdown();

        if(!executorService.awaitTermination(20, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }

        if(!executorServiceSubScriber.awaitTermination(20, TimeUnit.SECONDS)) {
            executorServiceSubScriber.shutdownNow();
        }

        //producer.publish(Topics.POLITICAL_NEWS,"Only Subscribe1 should receive this message");
    }

}
