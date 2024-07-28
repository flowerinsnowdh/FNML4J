package online.flowerinsnow.fnml4j.core;

public enum ParserType {
    OBJECT(1),
    LIST(2);
    public final int id;

    ParserType(int id) {
        this.id = id;
    }
}
