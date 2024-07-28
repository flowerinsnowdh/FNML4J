package online.flowerinsnow.fnml4j.api.node;

import online.flowerinsnow.fnml4j.api.exception.UnexpectedException;
import online.flowerinsnow.fnml4j.api.util.StringUtils;
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
public class ListNode implements IFNMLNode, Iterable<IFNMLNode> {
    @NotNull private ArrayList<IFNMLNode> list;

    /**
     * <p>创建一个空的、带名称的新的列表节点</p>
     */
    public ListNode() {
        this(new ArrayList<>());
    }

    /**
     * <p>创建一个带数据的、带mkig的新的列表节点</p>
     * @param list 数据
     */
    public ListNode(@NotNull List<IFNMLNode> list) {
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
            writer.write(StringUtils.repeat("    ", offset + 1));
            node.write(offset + 1, writer);
            writer.write("\n");
        }
        writer.write(StringUtils.repeat("    ", offset) + "]");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        ListNode that = (ListNode) object;
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
                "super=" + super.toString() +
                ", list=" + list +
                '}';
    }

    @Override
    public ListNode clone() {
        ListNode clone;
        try {
            clone = (ListNode) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnexpectedException(e);
        }
        //noinspection unchecked
        clone.list = (ArrayList<IFNMLNode>) this.list.clone();
        return clone;
    }
}
