package online.flowerinsnow.fnml4j.api.parser.present;

import online.flowerinsnow.fnml4j.api.exception.NodeParseException;
import online.flowerinsnow.fnml4j.api.node.ListNode;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.AbstractListNodeParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>将列表节点解析为字符串列表类型</p>
 */
public class StringListNodeParser extends AbstractListNodeParser<List<String>> {
    @Override
    public @Nullable List<String> parse(@NotNull ListNode node) throws NodeParseException {
        return node.getList().stream().collect(
                ArrayList::new,
                (list, n) -> list.add(((StringNode) n).getString()),
                ArrayList::addAll
        );
    }
}
