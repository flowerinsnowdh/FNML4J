package online.flowerinsnow.fnml4j.core;

import online.flowerinsnow.fnml4j.api.exception.ReservedException;
import online.flowerinsnow.fnml4j.api.exception.UnexpectedException;
import online.flowerinsnow.fnml4j.api.node.*;
import online.flowerinsnow.fnml4j.core.exception.ParseException;
import online.flowerinsnow.fnml4j.core.exception.ParseFailureCause;
import online.flowerinsnow.fnml4j.core.parse.ParseStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public abstract class FNML4J {
    private FNML4J() {
    }

    public static @NotNull ObjectNode parse(@NotNull String content) {
        Objects.requireNonNull(content);
        final ObjectNode rootNode = new ObjectNode();
        final ParseStack stack = new ParseStack();
        int line = 1;
        int column = 0;
        boolean escaping = false;
        StringBuilder inString = null; // 字符串中
        StringBuilder defining = null; // 定义字段名中
        String keyName = null; // 缓存字段名
        boolean beginOfLine = true; // 行首部
        boolean endOfLine = false; // 行末尾
        boolean singleLineComments = false; // 单行注释中
        char[] array = content.toCharArray();
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            column++;

            if (beginOfLine) { // 行首，未读取到任何信息或只读取到了空格
                IFNMLNode topNode0 = stack.getTopNodeNullable();
                if (topNode0 == null || topNode0 instanceof ObjectNode) { // 当前是对象节点
                    switch (c) {
                        case '#': // 开始注释
                            beginOfLine = false;
                            endOfLine = true;
                            singleLineComments = true;
                            break;
                        case '\n': // 空行
                            // 跳过本行，读取下一行
                            line++;
                            column = 0;
                            break;
                        case ' ':
                        case '\t': // 空格和缩进，忽略
                            break;
                        case '}': { // 闭合当前对象
                            if (topNode0 != null) { // 栈顶是对象节点，并且非root节点
                                String topName = stack.getTopName();
                                stack.pop();
                                IFNMLNode dataNode = stack.getTopNodeOptional().orElse(rootNode); // 再下一个节点，如果没有，获取root节点
                                if (dataNode instanceof ListNode) { // 列表节点
                                    ((ListNode) dataNode).add(topNode0);
                                } else if (dataNode instanceof ObjectNode) { // 对象节点
                                    ((ObjectNode) dataNode).set(topName, topNode0);
                                } else {
                                    FNML4J.throwParseException(ParseFailureCause.UNEXPECTED_CLOSING, line, column);
                                    throw new ReservedException();
                                }
                            } else {
                                FNML4J.throwParseException(ParseFailureCause.UNEXPECTED_CLOSING, line, column);
                                throw new ReservedException();
                            }
                            break;
                        }
                        case ']': // 闭合当前列表，但是顶端是对象，也就是异常的闭合
                            FNML4J.throwParseException(ParseFailureCause.UNEXPECTED_CLOSING, line, column);
                            throw new ReservedException();
                        default: // 开始定义字段
                            defining = new StringBuilder();
                            defining.append(c);
                            beginOfLine = false;
                            break;
                    }
                } else if (topNode0 instanceof ListNode) { // 是列表节点
                    switch (c) {
                        case '#': // 开始注释
                            beginOfLine = false;
                            endOfLine = true;
                            singleLineComments = true;
                            break;
                        case '\n': // 空行
                            // 跳过本行，读取下一行
                            line++;
                            column = 0;
                            break;
                        case ' ':
                        case '\t': // 空格和缩进，忽略
                            break;
                        case '}': { // 闭合当前对象，但是顶端是列表，也就是异常的闭合
                            FNML4J.throwParseException(ParseFailureCause.UNEXPECTED_CLOSING, line, column);
                            throw new ReservedException();
                        }
                        case ']': // 闭合当前列表
                            String topName = stack.getTopName();
                            stack.pop();
                            IFNMLNode dataNode = stack.getTopNodeOptional().orElse(rootNode); // 再下一个节点，如果没有，获取root节点
                            if (dataNode instanceof ListNode) { // 列表节点
                                ((ListNode) dataNode).add(topNode0);
                            } else if (dataNode instanceof ObjectNode) { // 对象节点
                                ((ObjectNode) dataNode).set(topName, topNode0);
                            } else {
                                FNML4J.throwParseException(ParseFailureCause.UNEXPECTED, line, column);
                                throw new ReservedException();
                            }
                        case '{': // 开始定义对象
                            beginOfLine = false;
                            endOfLine = true;
                            stack.push(null, new ObjectNode());
                            break;
                        case '[': // 开始定义列表
                            beginOfLine = false;
                            endOfLine = true;
                            stack.push(null, new ListNode());
                            break;
                        case '\'': // 开始定义字符串
                            beginOfLine = false;
                            inString = new StringBuilder();
                            break;
                        default: // 开始定义字段
                            throwParseException(ParseFailureCause.BAD_SYNTAX, line, column);
                            throw new UnexpectedException();
                    }
                }
            } else if (endOfLine) { // 单行末尾
                if ('\n' == c) { // 换行
                    line++;
                    column = 0;
                    beginOfLine = true;
                    endOfLine = false;
                } else if ('#' == c) { // 开始注释
                    singleLineComments = true;
                } else if (!singleLineComments && ' ' != c && '\t' != c) { // 不在注释中，且不是空格等无关紧要的内容
                    FNML4J.throwParseException(ParseFailureCause.BAD_SYNTAX, line, column);
                }
            } else if (inString != null) { // 正在字符串中
                if (escaping) { // 正在转义中
                    escaping = false;
                    switch (c) {
                        case '0': // 空字符 NUL
                            inString.append('\0');
                            break;
                        case 'b': // 退格符 Backspace
                            inString.append('\b');
                            break;
                        case 't': // 制表符 Tab
                            inString.append('\t');
                            break;
                        case 'n': // 换行符 Line feed
                            inString.append('\n');
                            break;
                        case 'f': // 换页符 Form feed
                            inString.append('\f');
                            break;
                        case 'r': // 回车符 Carriage return
                            inString.append('\r');
                            break;
                        case '\\': // 反斜线符 \
                            inString.append('\\');
                            break;
                        case '\'': // 单引号 '
                            inString.append('\'');
                            break;
                        default: // 其他符号：照搬
                            inString.append(c);
                            break;
                    }
                } else {
                    switch (c) {
                        case '\\': // 开始转义
                            escaping = true;
                            break;
                        case '\'': // 字符串结束
                            IFNMLNode topNode0 = stack.getTopNodeOptional()
                                    .orElse(rootNode);
                            if (topNode0 instanceof ObjectNode) {
                                ObjectNode topNode = (ObjectNode) topNode0;
                                topNode.set(keyName, new StringNode(inString.toString()));
                            } else if (topNode0 instanceof ListNode) {
                                ListNode topNode = (ListNode) topNode0;
                                topNode.add(new StringNode(inString.toString()));
                            } else {
                                FNML4J.throwParseException(ParseFailureCause.UNEXPECTED, line, column);
                                throw new UnexpectedException();
                            }
                            inString = null;
                            endOfLine = true;
                            break;
                        default:
                            inString.append(c);
                            break;
                    }
                }
            } else if (defining != null) { // 正在定义节点名
                switch (c) {
                    case '\'':  // 开始定义字符串
                        keyName = defining.toString().trim();
                        defining = null;
                        inString = new StringBuilder();
                        break;
                    case '{': // 开始定义对象
                        stack.push(defining.toString().trim(), new ObjectNode());
                        defining = null;
                        endOfLine = true;
                        break;
                    case '[': // 开始定义列表
                        stack.push(defining.toString().trim(), new ListNode());
                        defining = null;
                        endOfLine = true;
                        break;
                    default:
                        defining.append(c);
                        break;
                }
            }
        }
        return rootNode;
    }

    /*
    public static @NotNull ObjectNode parse(@NotNull String content) throws ParseException {
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
                            throwParseException(ParseFailureCause.BAD_SYNTAX, lineNumber, lineContent);
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
                            typeStack.removeLast(); // 从栈中弹出最顶类型
                            if (typeStack.isEmpty()) {
                                throwParseException(ParseFailureCause.END_OF_FILE, lineNumber, lineContent);
                                return null;
                            }
                            objectStack.removeLast();  // 从栈中弹出最新对象
                        } else if (lineContent.endsWith("[")) { // 开启新列表
                            ListNode node = new ListNode();
                            ListUtils.getLastOne(objectStack).set(name, node); // 将新列表压入栈
                            arrayStack.add(node);
                            typeStack.add(ParserType.LIST); // 将新类型压入栈
                        } else if (lineContent.equals("]")) { // 闭合栈最顶列表，但栈最顶是对象
                            throwParseException(ParseFailureCause.MISSING_CLOSING_BRACE, lineNumber, lineContent);
                            return null;
                        } else {
                            throwParseException(ParseFailureCause.BAD_SYNTAX, lineNumber, lineContent);
                        }
                    }
                    break;
                }
                case LIST: {
                    if (lineContent.startsWith("'") && lineContent.endsWith("'")) { // 是字符串类型
                        String value = lineContent;
                        if (!value.startsWith("'")) {
                            throwParseException(ParseFailureCause.BAD_SYNTAX, lineNumber, lineContent);
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
                        throwParseException(ParseFailureCause.MISSING_CLOSING_BRACE, lineNumber, lineContent);
                        return null;
                    } else if (lineContent.endsWith("[")) { // 开启新列表
                        ListNode node = new ListNode();
                        ListUtils.getLastOne(arrayStack).add(node); // 将新列表压入栈
                        arrayStack.add(node);
                        typeStack.add(ParserType.LIST); // 将新类型压入栈
                    } else if (lineContent.equals("]")) { // 闭合栈最顶列表
                        typeStack.removeLast(); // 从栈中弹出最顶类型
                        if (typeStack.isEmpty()) {
                            throwParseException(ParseFailureCause.END_OF_FILE, lineNumber, lineContent);
                            return null;
                        }
                        objectStack.remove(objectStack.size() - 1);  // 从栈中弹出最新对象
                    } else {
                        throwParseException(ParseFailureCause.BAD_SYNTAX, lineNumber, lineContent);
                    }
                    break;
                }
            }
        }
        if (typeStack.size() != 1) {
            throwParseException(ParseFailureCause.END_OF_FILE, lineNumber, lineContent);
        }
        return root;
    }
     */

    public static @NotNull ObjectNode parse(@NotNull File file, @NotNull Charset charset) throws IOException, ParseException {
        Objects.requireNonNull(file);
        Objects.requireNonNull(charset);
        return parse(file.toPath(), charset);
    }

    public static @NotNull ObjectNode parse(@NotNull Path path, @NotNull Charset charset) throws IOException, ParseException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(charset);
        return parse(new String(Files.readAllBytes(path), charset));
    }

    private static void throwParseException(@NotNull ParseFailureCause cause, int lineNumber, int lineColumn) {
        Objects.requireNonNull(cause);
        throw new ParseException("Failed to parse FNML: " + cause.message + " (line " + lineNumber + ":" + lineColumn);
    }
}
