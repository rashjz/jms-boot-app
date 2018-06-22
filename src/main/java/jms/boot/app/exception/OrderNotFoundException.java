package jms.boot.app.exception;

public class OrderNotFoundException extends Exception {

    private final int code;

    public OrderNotFoundException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public OrderNotFoundException(String message, int code) {
        super(message);
        this.code = code;
    }

    public OrderNotFoundException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
