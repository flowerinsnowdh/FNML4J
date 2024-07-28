package online.flowerinsnow.fnml4j.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class ASCIIUtils {
    private ASCIIUtils() {
    }

    public static String escape(@NotNull String string) {
        Objects.requireNonNull(string);
        return string.replace("\\0", "\0")
                .replace("\\b", "\b")
                .replace("\\t", "\t")
                .replace("\\n", "\n")
                .replace("\\f", "\f")
                .replace("\\r", "\r");
    }

    public static String unescape(@NotNull String string) {
        Objects.requireNonNull(string);
        return string.replace("\0", "\\0")
                .replace("\b", "\\b")
                .replace("\t", "\\t")
                .replace("\n", "\\n")
                .replace("\f", "\\f")
                .replace("\r", "\\r");
    }
}
