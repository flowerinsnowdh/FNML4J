package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.parser.FNMLStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为浮点数类型</p>
 */
public class FNMLFloatNodeParser extends FNMLStringNodeParser<Float> {
    @Override
    public @Nullable Float parse(@NotNull FNMLStringNode node) throws NodeParseException {
        try {
            return Float.parseFloat(node.getString());
        } catch (NumberFormatException e) {
            throw new NodeParseException(node, e);
        }
    }
}
