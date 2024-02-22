package online.flowerinsnow.fnml4j.api.node;

import online.flowerinsnow.fnml4j.api.exception.UnexpectedException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class NamedNode implements IFNMLNode, Cloneable {
    @NotNull private final String name;

    public NamedNode(@NotNull String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NamedNode that = (NamedNode) object;
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
        return "NamedNode{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public NamedNode clone() {
        try {
            return (NamedNode) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnexpectedException(e);
        }
    }
}
