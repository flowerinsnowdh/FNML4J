package online.flowerinsnow.fnml4j.api.parser.present;

public abstract class FNML4JPresentParser {
    private FNML4JPresentParser() {
    }

    public static final FNMLByteNodeParser BYTE = new FNMLByteNodeParser();
    public static final FNMLShortNodeParser SHORT = new FNMLShortNodeParser();
    public static final FNMLIntNodeParser INT = new FNMLIntNodeParser();
    public static final FNMLLongNodeParser LONG = new FNMLLongNodeParser();
    public static final FNMLFloatNodeParser FLOAT = new FNMLFloatNodeParser();
    public static final FNMLDoubleNodeParser DOUBLE = new FNMLDoubleNodeParser();
    public static final FNMLStringNodeParser STRING = new FNMLStringNodeParser();
    public static final FNMLUUIDNodeParser UUID = new FNMLUUIDNodeParser();
    public static final FNMLStringListNodeParser STRING_LIST = new FNMLStringListNodeParser();
}
