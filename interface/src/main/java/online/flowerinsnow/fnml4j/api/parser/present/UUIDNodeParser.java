package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.AbstractStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * <p>将字符串节点解析为UUID类型</p>
 */
public class UUIDNodeParser extends AbstractStringNodeParser<UUID> {
    @Override
    public @Nullable UUID parse(@NotNull StringNode node) throws NodeParseException {
        try {
            return UUID.fromString(node.getString());
        } catch (IllegalArgumentException e) {
            throw new NodeParseException(node, e);
        }
    }
}
