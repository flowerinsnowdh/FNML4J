package online.flowerinsnow.fnml4j.test;

import online.flowerinsnow.fnml4j.api.node.FNMLObjectNode;
import online.flowerinsnow.fnml4j.api.node.FNMLStringNode;
import online.flowerinsnow.fnml4j.api.node.IFNMLNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;

public class WriteTest {
    @Test
    public void test() throws IOException {
        LinkedHashMap<String, IFNMLNode> data = new LinkedHashMap<>();
        data.put("field1", new FNMLStringNode("field1", "Hello"));
        data.put("field2", new FNMLStringNode("field2", "World"));
        FNMLObjectNode node = new FNMLObjectNode("root", data);
        StringWriter writer = new StringWriter();
        node.writeRoot(0, writer);
        System.out.println(writer);
    }
}
