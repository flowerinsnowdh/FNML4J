package online.flowerinsnow.fnml4j.api.node;

import online.flowerinsnow.fnml4j.api.exception.UnexpectedException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * <p>代表一个FNML字符串节点</p>
 * <p>例如</p>
 * <pre>{@literal
 *     field1 'value1'
 * }</pre>
 */
public class StringNode implements IFNMLNode {
    @NotNull private String string;

    public StringNode(@NotNull String string) {
        this.string = Objects.requireNonNull(string);
    }

    /**
     * <p>获取字符串值</p>
     * @return 字符串值
     */
    public @NotNull String getString() {
        return string;
    }

    /**
     * <p>设置字符串值</p>
     * @param string 字符串值
     */
    public void setString(@NotNull String string) {
        this.string = Objects.requireNonNull(string);
    }

    @Override
    public void write(int offset, @NotNull Writer writer) throws IOException {
        writer.write("'" + this.string
                .replace("\\", "\\\\")
                .replace("\0", "\\0")
                .replace("\b", "\\b")
                .replace("\t", "\\t")
                .replace("\n", "\\n")
                .replace("\f", "\\f")
                .replace("\r", "\\r")
                .replace("'", "\\'")
                + "'");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        StringNode that = (StringNode) object;
        return string.equals(that.string);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + super.hashCode();
        result = 31 * result + string.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "StringNode{" +
                "string='" + string + '\'' +
                '}';
    }

    @Override
    public StringNode clone() {
        try {
            return (StringNode) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnexpectedException(e);
        }
    }
}
