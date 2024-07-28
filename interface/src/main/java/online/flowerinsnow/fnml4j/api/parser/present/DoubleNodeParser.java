package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.AbstractStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为双精度浮点数类型</p>
 */
public class DoubleNodeParser extends AbstractStringNodeParser<Double> {
    @Override
    public @Nullable Double parse(@NotNull StringNode node) throws NodeParseException {
        try {
            return Double.parseDouble(node.getString());
        } catch (NumberFormatException e) {
            throw new NodeParseException(node, e);
        }
    }
}
