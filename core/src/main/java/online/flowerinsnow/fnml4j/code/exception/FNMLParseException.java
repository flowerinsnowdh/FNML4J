package online.flowerinsnow.fnml4j.code.exception;

/**
 * <p>当FNML文件/字符串解释失败时抛出</p>
 */
public class FNMLParseException extends RuntimeException {
    public FNMLParseException() {
        super();
    }

    public FNMLParseException(String message) {
        super(message);
    }

    public FNMLParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FNMLParseException(Throwable cause) {
        super(cause);
    }
}
