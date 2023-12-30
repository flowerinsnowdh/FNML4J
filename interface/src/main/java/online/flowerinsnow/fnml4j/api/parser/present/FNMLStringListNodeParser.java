package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.FNMLListNode;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.parser.FNMLListNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>将列表节点解析为字符串列表类型</p>
 */
public class FNMLStringListNodeParser extends FNMLListNodeParser<List<String>> {
    @Override
    public @Nullable List<String> parse(@NotNull FNMLListNode node) throws NodeParseException {
        return node.getList().stream().collect(
                ArrayList::new,
                (list, n) -> list.add(((FNMLStringNode) n).getString()),
                ArrayList::addAll
        );
    }
}
