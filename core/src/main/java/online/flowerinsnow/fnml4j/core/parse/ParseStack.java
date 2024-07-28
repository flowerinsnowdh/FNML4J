package online.flowerinsnow.fnml4j.core.parse;

import online.flowerinsnow.fnml4j.api.exception.ReservedException;
import online.flowerinsnow.fnml4j.api.exception.UnacceptedNodeException;
import online.flowerinsnow.fnml4j.api.node.IFNMLNode;
import online.flowerinsnow.fnml4j.api.node.ListNode;
import online.flowerinsnow.fnml4j.api.node.ObjectNode;
import online.flowerinsnow.fnml4j.core.ParserType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ParseStack {
    private static final Map<Class<? extends IFNMLNode>, ParserType> ACCEPTED_TYPE = new HashMap<>();

    static {
        ACCEPTED_TYPE.put(ListNode.class, ParserType.LIST);
        ACCEPTED_TYPE.put(ObjectNode.class, ParserType.OBJECT);
    }

    private final List<IFNMLNode> stack = new ArrayList<>();
    private final List<String> stackNames = new ArrayList<>();

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public @NotNull String getTopName() {
        this.throwIfEmptyStack();
        return this.stackNames.get(this.stack.size() - 1);
    }

    public @NotNull IFNMLNode getTopNode() {
        this.throwIfEmptyStack();
        return this.stack.get(this.stack.size() - 1);
    }

    public @Nullable IFNMLNode getTopNodeNullable() {
        if (this.isEmpty()) {
            return null;
        }
        return this.stack.get(this.stack.size() - 1);
    }

    public @NotNull Optional<IFNMLNode> getTopNodeOptional() {
        return Optional.ofNullable(this.getTopNodeNullable());
    }

    public @NotNull ParserType getTopType() {
        this.throwIfEmptyStack(); // 检查：如果栈为空，抛出异常
        return ParseStack.getType(this.getTopNode().getClass())
                .orElseThrow(ReservedException::new);
    }
    
    public void push(@Nullable String name, @NotNull IFNMLNode node) {
        Objects.requireNonNull(node);
        if (ParseStack.ACCEPTED_TYPE.containsKey(node.getClass())) {
            this.stack.add(node);
            this.stackNames.add(name);
        } else {
            throw new UnacceptedNodeException(node.getClass().getName());
        }
    }
    
    public boolean pop() {
        this.throwIfEmptyStack(); // 检查：如果栈为空，抛出异常
        this.stack.remove(this.stack.size() - 1);
        this.stackNames.remove(this.stackNames.size() - 1);
        return this.stack.isEmpty();
    }

    public int size() {
        return this.stack.size();
    }

    public void throwIfEmptyStack() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        }
    }

    public static @NotNull Optional<ParserType> getType(@NotNull Class<? extends IFNMLNode> _class) {
        Objects.requireNonNull(_class);
        return Optional.ofNullable(ParseStack.ACCEPTED_TYPE.get(_class));
    }

    public static @NotNull Optional<Class<? extends IFNMLNode>> getClass(@NotNull ParserType type) {
        Objects.requireNonNull(type);
        for (Map.Entry<Class<? extends IFNMLNode>, ParserType> entry : ACCEPTED_TYPE.entrySet()) {
            if (entry.getValue() == type) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
}
