package online.flowerinsnow.fnml4j.api.parser;

import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;

/**
 * <p>通过解析字符串解析成Java对象</p>
 * @param <T> Java对象类型
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLByteNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLShortNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLIntNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLLongNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLFloatNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLDoubleNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLStringNodeParser
 * @see online.flowerinsnow.fnml4j.api.parser.present.FNMLUUIDNodeParser
 */
public abstract class FNMLStringNodeParser<T> implements IFNMLNodeParser<T, FNMLStringNode> {
}
