package online.flowerinsnow.fnml4j.core.exception;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum ParseFailureCause {
    BAD_SYNTAX(1, "Bad syntax"),
    END_OF_FILE(2, "End of file"),
    MISSING_CLOSING_BRACE(3, "Missing closing '}'"),
    MISSING_CLOSING_BRACKET(4, "Missing closing ']'"),
    /**
     * <p>意外的闭合</p>
     */
    UNEXPECTED_CLOSING(5, "Unexpected closing"),
    /**
     * <p>需要标识符</p>
     */
    IDENTIFIER_REQUIRED(6, "Identifier required"),
    UNEXPECTED(7, "Unexpected");
    public final int id;
    @NotNull public final String message;

    ParseFailureCause(int id, @NotNull String message) {
        this.id = id;
        this.message = Objects.requireNonNull(message);
    }
}
