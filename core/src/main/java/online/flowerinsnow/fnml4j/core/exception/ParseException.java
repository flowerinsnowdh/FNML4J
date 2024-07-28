package online.flowerinsnow.fnml4j.core.exception;

/**
 * <p>当FNML文件/字符串解释失败时抛出</p>
 */
public class ParseException extends RuntimeException {
    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
