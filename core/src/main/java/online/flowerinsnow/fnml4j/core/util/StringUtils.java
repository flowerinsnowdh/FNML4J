package online.flowerinsnow.fnml4j.core.util;

import org.jetbrains.annotations.NotNull;

public abstract class StringUtils {
    private StringUtils() {
    }

    public static String repeat(@NotNull String content, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            sb.append(content);
        }
        return sb.toString();
    }
}
