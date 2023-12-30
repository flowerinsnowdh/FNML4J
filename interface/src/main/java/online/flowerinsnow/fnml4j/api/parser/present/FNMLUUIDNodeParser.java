package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.parser.FNMLStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * <p>将字符串节点解析为UUID类型</p>
 */
public class FNMLUUIDNodeParser extends FNMLStringNodeParser<UUID> {
    @Override
    public @Nullable UUID parse(@NotNull FNMLStringNode node) throws NodeParseException {
        try {
            return UUID.fromString(node.getString());
        } catch (IllegalArgumentException e) {
            throw new NodeParseException(node, e);
        }
    }
}
