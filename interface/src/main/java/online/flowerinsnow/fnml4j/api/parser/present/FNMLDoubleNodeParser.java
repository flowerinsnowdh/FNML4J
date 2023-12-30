package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.parser.FNMLStringNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>将字符串节点解析为双精度浮点数类型</p>
 */
public class FNMLDoubleNodeParser extends FNMLStringNodeParser<Double> {
    @Override
    public @Nullable Double parse(@NotNull FNMLStringNode node) throws NodeParseException {
        try {
            return Double.parseDouble(node.getString());
        } catch (NumberFormatException e) {
            throw new NodeParseException(node, e);
        }
    }
}
