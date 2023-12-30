package online.flowerinsnow.fnml4j.api.node;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class NamedFNMLNode implements IFNMLNode {
    @NotNull private final String name;

    public NamedFNMLNode(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NamedFNMLNode that = (NamedFNMLNode) object;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NamedFNMLNode{" +
                "name='" + name + '\'' +
                '}';
    }
}
