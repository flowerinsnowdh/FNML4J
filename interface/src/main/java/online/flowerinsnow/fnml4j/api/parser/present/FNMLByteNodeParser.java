package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.parser.FNMLStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为字节整数类型</p>
 */
public class FNMLByteNodeParser extends FNMLStringNodeParser<Byte> {
    @Override
    public @Nullable Byte parse(@NotNull FNMLStringNode node) throws NodeParseException {
        try {
            return Byte.parseByte(node.getString());
        } catch (NumberFormatException e) {
            throw new NodeParseException(node, e);
        }
    }
}
