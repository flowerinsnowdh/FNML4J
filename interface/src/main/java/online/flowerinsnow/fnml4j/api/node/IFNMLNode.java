package online.flowerinsnow.fnml4j.api.node;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

/**
 * <p>代表一个FNML节点</p>
 */
public interface IFNMLNode {
    /**
     * <p>把当前节点写入流</p>
     * @param offset 缩进
     * @param writer 流
     */
    void write(int offset, @NotNull Writer writer) throws IOException;
}
