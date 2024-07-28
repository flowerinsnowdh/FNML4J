package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.AbstractStringNodeParser;
import org.jetbrains.annotations.NotNull;

/**
 * <p>将字符串节点解析为布尔类型</p>
 */
public class BooleanNodeParser extends AbstractStringNodeParser<Boolean> {
    @Override
    public @NotNull Boolean parse(@NotNull StringNode node) throws NodeParseException {
        return Boolean.parseBoolean(node.getString());
    }
}
