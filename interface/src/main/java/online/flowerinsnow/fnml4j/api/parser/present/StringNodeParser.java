package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.AbstractStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为字符串类型</p>
 */
public class StringNodeParser extends AbstractStringNodeParser<String> {
    @Override
    public @Nullable String parse(@NotNull StringNode node) throws NodeParseException {
        return node.getString();
    }
}
