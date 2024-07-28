package online.flowerinsnow.fnml4j.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public abstract class ListUtils {
    private ListUtils() {
    }

    public static <T> T getLastOne(@NotNull List<T> list) {
        Objects.requireNonNull(list);
        return list.get(list.size() - 1);
    }
}
