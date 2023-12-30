package online.flowerinsnow.fnml4j.api.node;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * <p>代表一个列表节点</p>
 * <p>例如</p>
 * <pre>{@literal
 *     list1 [
 *         'element0'
 *         'element1'
 *     ]
 * }</pre>
 * <p><strong>非线程安全类，遍历时不会快速失败</strong></p>
 */
public class FNMLListNode extends NamedFNMLNode implements Iterable<IFNMLNode> {
    @NotNull private final List<IFNMLNode> list;

    /**
     * <p>创建一个空的、带名称的新的列表节点</p>
     * @param name 名称
     */
    public FNMLListNode(@NotNull String name) {
        this(name, new ArrayList<>());
    }

    /**
     * <p>创建一个带数据的、带mkig的新的列表节点</p>
     * @param name 名称
     * @param list 数据
     */
    public FNMLListNode(@NotNull String name, @NotNull List<IFNMLNode> list) {
        super(name);
        Objects.requireNonNull(list);
        this.list = new ArrayList<>(list);
    }

    /**
     * <p>获取当前列表为Java List</p>
     * @return 当前列表，转为Java List，返回的是一个新的不可修改的列表对象
     */
    public @NotNull List<IFNMLNode> getList() {
        return Collections.unmodifiableList(list);
    }

    public void add(@NotNull IFNMLNode element) {
        Objects.requireNonNull(element);
        list.add(element);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void remove(@NotNull IFNMLNode element) {
        Objects.requireNonNull(element);
        list.remove(element);
    }

    public @NotNull IFNMLNode get(int index) {
        return getList().get(index);
    }

    public void set(int index, @NotNull IFNMLNode element) {
        list.set(index, element);
    }

    public int size() {
        return list.size();
    }

    @NotNull
    @Override
    public Iterator<IFNMLNode> iterator() {
        return getList().iterator();
    }

    @Override
    public void write(int offset, @NotNull Writer writer) throws IOException {
        writer.write("[\n");
        for (IFNMLNode node : getList()) {
            writer.write("    ".repeat(offset + 1));
            node.write(offset + 1, writer);
            writer.write("\n");
        }
        writer.write("    ".repeat(offset) + "]");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        FNMLListNode that = (FNMLListNode) object;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + super.hashCode();
        result = 31 * result + list.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FNMLListNode{" +
                "super=" + super.hashCode() +
                ", list=" + list +
                '}';
    }
}
