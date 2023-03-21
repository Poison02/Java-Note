package stack.arraystack;

/**
 * 
 * 该类是自定义异常类，在判断栈为空时，抛出该异常
 * 
 */

public class StackEmptyException extends RuntimeException{
    
    public StackEmptyException() {
        super();
    }

    public StackEmptyException(String message) {
        super(message);
    }

    public StackEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public StackEmptyException(Throwable cause) {
        super(cause);
    }

    protected StackEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
