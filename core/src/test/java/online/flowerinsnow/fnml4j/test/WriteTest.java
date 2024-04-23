package online.flowerinsnow.fnml4j.test;

import online.flowerinsnow.fnml4j.api.node.ObjectNode;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.api.node.IFNMLNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;

public class WriteTest {
    @Test
    public void test() throws IOException {
        LinkedHashMap<String, IFNMLNode> data = new LinkedHashMap<>();
        data.put("field1", new StringNode("Hello"));
        data.put("field2", new StringNode("World"));
        ObjectNode node = new ObjectNode(data);
        StringWriter writer = new StringWriter();
        node.writeRoot(0, writer);
        System.out.println(writer);
    }
}
