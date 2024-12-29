package pubsub.exception;

public class PubSubException extends Exception {

    PubSubException(String message) {
       super(message);
    }

    PubSubException(String message, Throwable cause) {
        super(message, cause);
    }
}
