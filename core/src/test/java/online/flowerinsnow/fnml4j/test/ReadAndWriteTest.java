package online.flowerinsnow.fnml4j.test;

import online.flowerinsnow.fnml4j.api.node.ObjectNode;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.parser.present.FNML4JPresentParser;
import online.flowerinsnow.fnml4j.code.FNML4J;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

public class ReadAndWriteTest {
    @Test
    public void test() throws IOException {
        String content = "# 我是注释\nfield1 '20231231'\nfield2 'value2'\nobject3 {\n    field4 'value4'\n    list5 [\n    '456'\n    '789'\n]\n}";
        ObjectNode node = FNML4J.parse(content);
        StringWriter sw = new StringWriter();
        node.writeRoot(0, sw);
        System.out.println(node.getChildNodeNotNull("field1", FNML4JPresentParser.INT, StringNode.class));
        System.out.println(sw);
    }
}
