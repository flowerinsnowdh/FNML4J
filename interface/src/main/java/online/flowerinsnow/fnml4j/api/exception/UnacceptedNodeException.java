package online.flowerinsnow.fnml4j.api.exception;

public class UnacceptedNodeException extends RuntimeException {
    public UnacceptedNodeException() {
        super();
    }

    public UnacceptedNodeException(String message) {
        super(message);
    }

    public UnacceptedNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnacceptedNodeException(Throwable cause) {
        super(cause);
    }
}
