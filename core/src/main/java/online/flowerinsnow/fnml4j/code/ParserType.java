package online.flowerinsnow.fnml4j.code;

public enum ParserType {
    OBJECT(1),
    LIST(2);
    public final int id;

    ParserType(int id) {
        this.id = id;
    }
}
