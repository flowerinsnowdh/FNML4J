package online.flowerinsnow.fnml4j.core;

import online.flowerinsnow.fnml4j.api.node.ListNode;
import online.flowerinsnow.fnml4j.api.node.ObjectNode;
import online.flowerinsnow.fnml4j.api.node.StringNode;
import online.flowerinsnow.fnml4j.core.exception.FNMLParseException;
import online.flowerinsnow.fnml4j.core.util.ASCIIUtils;
import online.flowerinsnow.fnml4j.core.util.ListUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public abstract class FNML4J {
    private FNML4J() {
    }

    public static @NotNull ObjectNode parse(@NotNull String content) throws FNMLParseException {
        Objects.requireNonNull(content);
        final ArrayList<ObjectNode> objectStack = new ArrayList<>();
        final ArrayList<ListNode> arrayStack = new ArrayList<>();
        final ArrayList<ParserType> typeStack = new ArrayList<>();
        final ObjectNode root = new ObjectNode();
        typeStack.add(ParserType.OBJECT);
        objectStack.add(root);
        int lineNumber = 0;
        String[] lines = content.split("\n");
        String lineContent = null;
        for (String line : lines) {
            lineNumber++;
            lineContent = line.trim();
            if (lineContent.isEmpty() || lineContent.startsWith("# ")) { // 是空行或者注释行
                continue;
            }
            switch (ListUtils.getLastOne(typeStack)) {
                case OBJECT: {
                    int indexOf = lineContent.indexOf("'");
                    if (indexOf != lineContent.length() - 1 && lineContent.endsWith("'")) { // 是字符串类型
                        String name = lineContent.substring(0, indexOf).trim();
                        String value = lineContent.substring(indexOf).trim();
                        if (!value.startsWith("'")) {
                            throwParseException("Bad syntax", lineNumber, lineContent);
                            return null;
                        }
                        value = ASCIIUtils.escape(value.substring(1, value.length() - 1)); // 去掉开头的单引号、转义
                        ListUtils.getLastOne(objectStack).set(name, new StringNode(value));
                    } else {
                        String name = lineContent.substring(0, lineContent.length() - 1).trim();
                        if (lineContent.endsWith("{")) { // 开启新对象
                            ObjectNode node = new ObjectNode();
                            ListUtils.getLastOne(objectStack).set(name, node);
                            objectStack.add(node); // 将新对象压入栈
                            typeStack.add(ParserType.OBJECT); // 将新类型压入栈
                        } else if (lineContent.equals("}")) { // 闭合最新对象
                            typeStack.remove(typeStack.size() - 1); // 从栈中弹出最顶类型
                            if (typeStack.isEmpty()) {
                                throwParseException("End of file", lineNumber, lineContent);
                                return null;
                            }
                            objectStack.remove(objectStack.size() - 1);  // 从栈中弹出最新对象
                        } else if (lineContent.endsWith("[")) { // 开启新列表
                            ListNode node = new ListNode();
                            ListUtils.getLastOne(objectStack).set(name, node); // 将新列表压入栈
                            arrayStack.add(node);
                            typeStack.add(ParserType.LIST); // 将新类型压入栈
                        } else if (lineContent.equals("]")) { // 闭合栈最顶列表，但栈最顶是对象
                            throwParseException("'}' required.", lineNumber, lineContent);
                            return null;
                        } else {
                            throwParseException("Bad syntax", lineNumber, lineContent);
                        }
                    }
                    break;
                }
                case LIST: {
                    if (lineContent.startsWith("'") && lineContent.endsWith("'")) { // 是字符串类型
                        String value = lineContent;
                        if (!value.startsWith("'")) {
                            throwParseException("Bad syntax", lineNumber, lineContent);
                            return null;
                        }
                        value = ASCIIUtils.escape(value.substring(1, lineContent.length() - 1)); // 去掉开头的单引号、转义
                        ListUtils.getLastOne(arrayStack).add(new StringNode(value));
                    } else if (lineContent.endsWith("{")) { // 开启新对象
                        ObjectNode node = new ObjectNode();
                        ListUtils.getLastOne(arrayStack).add(node);
                        objectStack.add(node); // 将新对象压入栈
                        typeStack.add(ParserType.OBJECT); // 将新类型压入栈
                    } else if (lineContent.equals("}")) { // 闭合最新对象，但栈最顶是列表
                        throwParseException("']' required.", lineNumber, lineContent);
                        return null;
                    } else if (lineContent.endsWith("[")) { // 开启新列表
                        ListNode node = new ListNode();
                        ListUtils.getLastOne(arrayStack).add(node); // 将新列表压入栈
                        arrayStack.add(node);
                        typeStack.add(ParserType.LIST); // 将新类型压入栈
                    } else if (lineContent.equals("]")) { // 闭合栈最顶列表
                        typeStack.remove(typeStack.size() - 1); // 从栈中弹出最顶类型
                        if (typeStack.isEmpty()) {
                            throwParseException("End of file", lineNumber, lineContent);
                            return null;
                        }
                        objectStack.remove(objectStack.size() - 1);  // 从栈中弹出最新对象
                    } else {
                        throwParseException("Bad syntax", lineNumber, lineContent);
                    }
                    break;
                }
            }
        }
        if (typeStack.size() != 1) {
            throwParseException("End of file", lineNumber, lineContent);
        }
        return root;
    }

    public static @NotNull ObjectNode parse(@NotNull File file, @NotNull Charset charset) throws IOException, FNMLParseException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(charset);
        return parse(file.toPath(), charset);
    }

    public static @NotNull ObjectNode parse(@NotNull Path path, @NotNull Charset charset) throws IOException, FNMLParseException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(charset);
        return parse(new String(Files.readAllBytes(path), charset));
    }

    private static void throwParseException(@Nullable String message, int lineNumber, @Nullable String lineContent) {
        throw new FNMLParseException("Failed to parse FNML: " + message + " (line " + lineNumber + ": '" + lineContent + "')");
    }
}
