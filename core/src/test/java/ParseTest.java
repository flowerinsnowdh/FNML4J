import online.flowerinsnow.fnml4j.api.node.ListNode;
import online.flowerinsnow.fnml4j.api.node.ObjectNode;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.core.FNML4J;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;

public class ParseTest {
    @Test
    void test() {
        ObjectNode parse = FNML4J.parse("object [\n" +
                "    'Hello' # Test\n" +
                "    {\n" +
                "        field2 'flowerinsnow\\''\n" +
                "    }\n" +
                "]");
        StringWriter sw = new StringWriter();
        try {
            parse.writeRoot(0, sw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sw);
    }
}
