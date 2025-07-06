package pubsub.poll;

import pubsub.subscribe.Subscriber;
import pubsub.subscribe.SubscriberStatus;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static pubsub.utility.CommonUtilities.isSubscriberOffline;
import static pubsub.utility.CommonUtilities.isSubscriberOnline;

public class SubScriberStatusChangePoller implements Runnable {

    Subscriber subscriber;
    boolean toggleOnlineOfflineStatus;
    Duration goOfflineAfterSeconds;
    Duration comeOnBackAfterSeconds;

    public SubScriberStatusChangePoller(Subscriber subscriber, boolean toggleOnlineOfflineStatus,
                                        Duration goOfflineAfterSeconds,  Duration comeOnBackAfterSeconds ) {
        this.subscriber = subscriber;
        this.toggleOnlineOfflineStatus = toggleOnlineOfflineStatus;
        this.goOfflineAfterSeconds = goOfflineAfterSeconds;
        this.comeOnBackAfterSeconds = comeOnBackAfterSeconds;
    }

    @Override
    public void run() {
        while(toggleOnlineOfflineStatus) {
            // if the subscriber status is online, change it to offline after goOfflineAfterSeconds
            if (isSubscriberOnline.test(subscriber)) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        subscriber.setSubscriberStatus(SubscriberStatus.OFFLINE);
                    }
                };
                Timer timer = new Timer("Timer");

                timer.schedule(task, goOfflineAfterSeconds.toMillis());
            }

            // if the subscriber status is offline, change it to online after comeOnBackAfterSeconds
            if (isSubscriberOffline.test(subscriber)) {
                TimerTask task = new TimerTask() {
                    public void run() {
                        subscriber.setSubscriberStatus(SubscriberStatus.ONLINE);
                    }
                };
                Timer timer = new Timer("Timer");

                timer.schedule(task, comeOnBackAfterSeconds.toMillis());
            }
        }
    }


}
