package online.flowerinsnow.fnml4j.api.node;

import online.flowerinsnow.fnml4j.api.exception.WrongNodeTypeException;
import online.flowerinsnow.fnml4j.api.parser.IFNMLNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * <p>代表一个对象节点</p>
 * <p>例如</p>
 * <pre>{@literal
 *     object1 {
 *         field1 = 'value1'
 *         field2 = 'value2'
 *     }
 * }</pre>
 */
public class FNMLObjectNode extends NamedFNMLNode {
    private final Map<String, IFNMLNode> childNodes;

    /**
     * <p>创建一个空的、带名称的FNML对象节点</p>
     * @param name 名称
     */
    public FNMLObjectNode(@NotNull String name) {
        this(name, new LinkedHashMap<>());
    }

    /**
     * <p>创建一个带数据的、带名称的FNML对象节点</p>
     * @param name 名称
     * @param childNodes 数据
     */
    public FNMLObjectNode(@NotNull String name, @NotNull Map<String, IFNMLNode> childNodes) {
        super(name);
        Objects.requireNonNull(childNodes);
        this.childNodes = new LinkedHashMap<>(childNodes);
    }

    /**
     * <p>获取所有子节点</p>
     * @return 一个不可修改的所有子节点集合
     */
    public @NotNull Map<String, ? extends IFNMLNode> getChildNodes() {
        return childNodes;
    }

    /**
     * <p>获取指定名称的子节点</p>
     * @param name 指定名称
     * @return 指定名称的子节点
     * @param <T> 子节点类型
     */
    public <T extends IFNMLNode> @Nullable T getChildNode(@NotNull String name) {
        Objects.requireNonNull(name);
        //noinspection unchecked
        return (T) childNodes.get(name);
    }

    /**
     * <p>获取指定名称的子节点，并检查非空</p>
     * @param name 指定名称
     * @return 指定名称的子节点
     * @param <T> 子节点类型
     * @see FNMLObjectNode#getChildNode(String) 
     */
    public <T extends IFNMLNode> @NotNull T getChildNodeNotNull(@NotNull String name) {
        Objects.requireNonNull(name);
        return Objects.requireNonNull(getChildNode(name));
    }

    /**
     * <p>获取指定名称的子节点，并解析为Java类型</p>
     * <p>使用传参parser作为解析器</p>
     * <p>子节点类型必须为nodeType或子类，否则将会抛出{@link WrongNodeTypeException}</p>
     *
     * @param name 指定名称
     * @param parser 解析器
     * @param nodeType 需要的
     * @return 指定名称的子节点，并解析为Java类型
     * @param <T> Java类型
     * @param <N> 节点类型
     * @param <P> 解析器类型
     * @see online.flowerinsnow.fnml4j.api.parser.present.FNML4JPresentParser 这里有一些预设解析器供参考
     */
    public <T, N extends IFNMLNode, P extends IFNMLNodeParser<T, N>> @Nullable T getChildNode(@NotNull String name, @NotNull P parser, @NotNull Class<N> nodeType) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(parser);
        @Nullable IFNMLNode childNode = getChildNode(name);
        if (childNode == null) {
            return null;
        }
        if (!nodeType.isInstance(childNode)) {
            throw new WrongNodeTypeException(childNode, nodeType.getName() + " expected, but found " + childNode.getClass());
        }
        @NotNull N node = nodeType.cast(childNode);
        return parser.parse(node);
    }

    /**
     * <p>获取子节点的键集合</p>
     * @return 子节点的键集合
     */
    public @NotNull Set<String> keySet() {
        return childNodes.keySet();
    }

    public void set(@NotNull String name, @NotNull IFNMLNode node) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(node);
        childNodes.put(name, node);
    }

    /**
     * <p>获取指定名称的子节点，并解析为Java类型，并检查非空</p>
     * <p>使用传参parser作为解析器</p>
     * <p>子节点类型必须为nodeType或子类，否则将会抛出{@link WrongNodeTypeException}</p>
     *
     * @param name 指定名称
     * @param parser 解析器
     * @param nodeType 需要的
     * @return 指定名称的子节点，并解析为Java类型
     * @param <T> Java类型
     * @param <N> 节点类型
     * @param <P> 解析器类型
     * @see FNMLObjectNode#getChildNode(String, IFNMLNodeParser, Class)
     */
    public <T, N extends IFNMLNode, P extends IFNMLNodeParser<T, N>> @Nullable T getChildNodeNotNull(@NotNull String name, @NotNull P parser, @NotNull Class<N> nodeType) {
        return Objects.requireNonNull(getChildNode(name, parser, nodeType));
    }

    @Override
    public void write(int offset, @NotNull Writer writer) throws IOException {
        writer.write("{\n");
        writeRoot(offset + 1, writer);
        writer.write("    ".repeat(offset) + "}");
    }

    public void writeRoot(int offset, @NotNull Writer writer) throws IOException {
        for (Map.Entry<String, ? extends IFNMLNode> entry : getChildNodes().entrySet()) {
            String name = entry.getKey();
            IFNMLNode node = entry.getValue();
            writer.write("    ".repeat(offset) + name + " ");
            node.write(offset, writer);
            writer.write("\n");
        }
    }
}
