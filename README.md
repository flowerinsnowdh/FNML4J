# FNML
FNML全名为`Flower's Node Markdown Language`，是由[@冬花ice](https://github.com/flowerinsnowdh)原创的一种标记语言，它做到了最简、同时支持解析和写入

## 格式语法
<details>
<summary>语法格式</summary>

FNML，顾名思义是由节点组成的配置文件

### 注释
不支持行内注释，必须以`# `开头（井号后面必须有个空格，前面可以缩进）

注释行会在解析时被自动忽略

<details open="open">
<summary>下面的2种注释是合法的</summary>

```text
# 用户名
username 'flowerinsnow'
```

```text
profile {
    # 用户名
    username 'flowerinsnow'
}
```

</details>

<details open="open">
<summary>下面这2种注释是非法的</summary>

```text
username 'flowerinsnow' # 用户名
```

```text
#用户名
username 'flowerinsnow'
```

</details>

### 节点
节点有三种，分别是文本节点、对象节点、列表节点

对于数字，FNML并不支持，请使用文本节点

#### 文本节点
顾名思义，文本节点包含单行文本

格式要求以`'`开头，以`'`结尾，中间可以有`'`，以换行前结束

注：FNML不支持多行文本，请使用`\n`转义

<details open="open">
<summary>下面这3种文本节点是合法的</summary>

```text
field1 '20231231'
```

```text
# 内容会是`'value1'`（包含内侧的单引号）
field2 ''value1''
```

```text
# 内容会是`value2\n`（不会有任何转义）
field3 'value2\n'
```

</details>

<details open="open">
<summary>下面这4种文本节点是非法的</summary>

```text
# 使用非英文单引号
field1 ‘value1’
```

```text
# 换行
field2 '
value2
'
```

```text
# 不使用单引号
field4 18
```


```text
# 不以单引号结尾
field3 'valu'e3
```

</details>

## 对象节点

必须以`{`开头，并立即换行，必须以单行`}`结尾

<details open="open">
<summary>合法格式</summary>

```text
object1 {
    field1 'value1'
    object2 {
        field2 'value2'
    }
}
```

</details>

<details open="open">
<summary>以下这3种格式是非法的</summary>

```text
# `{`不与字段名一行
object1
{
    field1 'value1'
}
```

```text
# `{`不在该行最后
object2 { field2 'value2'

}
```

```text
# ``}`不在单独一行
object3 {
field3 'value3'}
```

</details>

### 列表节点
<details open="open">
<summary>合法格式</summary>

```text
list1 [
    'value1'
    object2 {
        field3 'value3'    
    }
    list4 [
        'value5'
    ]
]
```

</details>

<details open="open">
<summary>以下这4种格式是非法的</summary>

```text
# `[`不与字段名一行
list
[
    'value1'
]
```

```text
# `[`不在该行最后
list2 [ 'value2'

]
```

```text
# ``}`不在单独一行
list3 [
    'value3']
```

```text
list4 [
    # 有字段名
    field4 'value4'
]
```

</details>

### 其他要求
1. 一行只能由一个节点
2. 对象和列表的`{``[`都必须使用对应的`}``]`闭合

</details>

# 为什么创建FNML？
常用的配置文件都无法满足我的需求

<details>
<summary>让我不满意的配置文件语言</summary>

## JSON、XML
这两个语言太过繁琐，很难编写，更很难让不懂编程的人编写

## YML
不太喜欢这个语言的缩进格式

## Properties、INI、TOML
很难或者根本不支持二级嵌套，类似这样

```text
object1 {
    object2 {
        field3: 'value4'    
    }
}
```

## HOCON
在我眼里是非常完美的一种语言，非常可惜用于读取它的Java库都不合我意，不仅是API好用程度，它在写入文件时还会给键排序，无法禁用这个排序

</details>

以后我打算在我自己的项目中都用这个格式了

# FNML4J
[@冬花ice](https://github.com/flowerinsnowdh)写的Java解析器，当然，我就是冬花ice。

我知道这个配置格式虽然在我眼中是完美的，但是不会有人注意渺小的我提出的这个格式，所以决定自己写解析器

