package online.flowerinsnow.fnml4j.api.exception;

/**
 * <p>Reserved exception</p>
 * <p>It should not appear unless there is something wrong with the program logic.</p>
 */
public class ReservedException extends RuntimeException {
    public ReservedException() {
        super();
    }

    public ReservedException(String message) {
        super(message);
    }

    public ReservedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservedException(Throwable cause) {
        super(cause);
    }
}
