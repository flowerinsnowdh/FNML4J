package online.flowerinsnow.fnml4j.api.parser.present;

public abstract class FNML4JPresentParser {
    private FNML4JPresentParser() {
    }

    public static final BooleanNodeParser BOOLEAN = new BooleanNodeParser();
    public static final ByteNodeParser BYTE = new ByteNodeParser();
    public static final ShortNodeParser SHORT = new ShortNodeParser();
    public static final IntNodeParser INT = new IntNodeParser();
    public static final LongNodeParser LONG = new LongNodeParser();
    public static final FloatNodeParser FLOAT = new FloatNodeParser();
    public static final DoubleNodeParser DOUBLE = new DoubleNodeParser();
    public static final StringNodeParser STRING = new StringNodeParser();
    public static final UUIDNodeParser UUID = new UUIDNodeParser();
    public static final StringListNodeParser STRING_LIST = new StringListNodeParser();
}
