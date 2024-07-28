package online.flowerinsnow.fnml4j.api.parser;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.IFNMLNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>解析FNML节点为Java类型的解析器</p>
 * @param <T> Java类型
 * @param <N> 节点类型
 * @see AbstractStringNodeParser
 * @see AbstractListNodeParser
 */
public interface IFNMLNodeParser<T, N extends IFNMLNode> {
    /**
     * <p>将节点类型解析为Java类型</p>
     * @param node 节点
     * @return Java类型
     * @throws NodeParseException 解析失败时抛出
     */
    @Nullable T parse(@NotNull N node) throws NodeParseException;
}
