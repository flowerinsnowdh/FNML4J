package online.flowerinsnow.fnml4j.api.exception;

import online.flowerinsnow.fnml4j.api.node.IFNMLNode;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WrongNodeTypeException extends RuntimeException {
    @NotNull private final IFNMLNode node;
    public WrongNodeTypeException(@NotNull IFNMLNode node) {
        super();
        this.node = Objects.requireNonNull(node);
    }

    public WrongNodeTypeException(@NotNull IFNMLNode node, String message) {
        super(message);
        this.node = Objects.requireNonNull(node);
    }

    public WrongNodeTypeException(@NotNull IFNMLNode node, String message, Throwable cause) {
        super(message, cause);
        this.node = Objects.requireNonNull(node);
    }

    public WrongNodeTypeException(@NotNull IFNMLNode node, Throwable cause) {
        super(cause);
        this.node = Objects.requireNonNull(node);
    }

    public @NotNull IFNMLNode getNode() {
        return node;
    }
}
