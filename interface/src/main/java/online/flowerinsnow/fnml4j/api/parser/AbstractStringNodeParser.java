package online.flowerinsnow.fnml4j.api.parser;

import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.present.*;

/**
 * <p>通过解析字符串解析成Java对象</p>
 * @param <T> Java对象类型
 * @see BooleanNodeParser
 * @see ByteNodeParser
 * @see ShortNodeParser
 * @see IntNodeParser
 * @see LongNodeParser
 * @see FloatNodeParser
 * @see DoubleNodeParser
 * @see StringNodeParser
 * @see UUIDNodeParser
 */
public abstract class AbstractStringNodeParser<T> implements IFNMLNodeParser<T, StringNode> {
}
