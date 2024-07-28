package online.flowerinsnow.fnml4j.api.parser;

import online.flowerinsnow.fnml4j.api.node.ListNode;
import online.flowerinsnow.fnml4j.api.parser.present.StringListNodeParser;

/**
 * <p>通过解析列表解析成Java对象</p>
 * @param <T> Java对象类型
 * @see StringListNodeParser
 */
public abstract class AbstractListNodeParser<T> implements IFNMLNodeParser<T, ListNode> {
}
