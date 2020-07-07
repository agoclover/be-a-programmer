# T-day24
## 1. 写出下列流 : 

字符输入流, 字节输出流, 字符输出流, 字节输入流. 

`Reader, Writer, OutputStream, InputStream`

字符文件输入流, 字节文件输出流, 字符文件输出流, 字节文件输入流. 

`FileReader, FileOutputStream, FileWriter, FileInputStream`

缓冲字符输入流, 缓冲字节输出流, 缓冲字符输出流, 缓冲字节输入流.

`BufferedReader, BufferedOutputStream, BufferedWriter, BufferedInputStream`

字节到字符转换输入流, 字节到字符转换输出流.

`InputStreamReader, OutputStreamWriter`

## 2. 编码是什么 ? 解码又是什么? 分别调用什么方法实现? 如果要按照指定编码方式处理, 又该如何?

编码: 将字符串以某种方式转换为字节数组 byte[]. 

解码: 将字节数组 byte[] 以某种方式转换成字符串. 

```java
# 编码实现
String str = "abc";
byte[] bytesArr = str.getBytes("编码方式名称");

# 解码实现
String newStr = new String(bytesArr, "编码方式名称");
``` 

或者调用 `OutputStreamWriter.(FileWriter, "utf8")` 和 `InputstreamReader.(FileWriter, "utf8")`

## 3. 用 `FileReader` 和 `FileWriter` 能处理各种不同编码的文本文件吗? 如果要处理各种不同的编码的文本文件, 如何做?

不能, 要用 `OutputStreamWriter.(FileWriter, "utf8")` 和 `InputstreamReader.(FileWriter, "utf8")`

## 4. 如何在一个文件中分别写入 8 个基本数据类型的值? 如何写一个 `utf8` 编码的字符串?

`ObjectOutputStream oos` 或 `DataOutputStream dos`:

```java
// 以下四个都是以 int val 为参数的
oos.writeByte(1); 
oos.writeShort(2);
oos.writeInt(4);
oos.writeChar('a');
oos.writeLong(8); // long val
oos.writeFloat(4f); // float val
oos.writeDouble(8.0); // double val
oos.writeBoolean(true); // boolean val
oos.writeUTF(String str);
```

## 5. 什么是对象序列化, 反序列化? 用代码如何实现?












