package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.parser.FNMLStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为长整数类型</p>
 */
public class FNMLLongNodeParser extends FNMLStringNodeParser<Long> {
    @Override
    public @Nullable Long parse(@NotNull FNMLStringNode node) throws NodeParseException {
        try {
            return Long.parseLong(node.getString());
        } catch (NumberFormatException e) {
            throw new NodeParseException(node, e);
        }
    }
}
