package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.AbstractStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为字节整数类型</p>
 */
public class ByteNodeParser extends AbstractStringNodeParser<Byte> {
    @Override
    public @Nullable Byte parse(@NotNull StringNode node) throws NodeParseException {
        try {
            return Byte.parseByte(node.getString());
        } catch (NumberFormatException e) {
            throw new NodeParseException(node, e);
        }
    }
}
