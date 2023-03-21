package queue.linkedqueue;

/**
 * 
 * 该类是自定义异常类，当队列为空时就抛出此异常
 * 
 */

public class QueueEmptyException extends RuntimeException{
    
    public QueueEmptyException() {
        super();
    }

    public QueueEmptyException(String message) {
        super(message);
    }

    public QueueEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueueEmptyException(Throwable cause) {
        super(cause);
    }

    protected QueueEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
