package pubsub.utility;

import pubsub.subscribe.Subscriber;
import pubsub.subscribe.SubscriberStatus;

import java.util.function.Predicate;

public class CommonUtilities {

    public static Predicate<Subscriber> isSubscriberOnline =
            subscriber1 -> subscriber1.getSubscriberStatus().compareTo(SubscriberStatus.ONLINE) == 0;

    public static Predicate<Subscriber> isSubscriberOffline =
            subscriber1 -> subscriber1.getSubscriberStatus().compareTo(SubscriberStatus.OFFLINE) == 0;
}
