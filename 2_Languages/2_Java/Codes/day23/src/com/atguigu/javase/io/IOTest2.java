package com.atguigu.javase.io;

import org.junit.Test;

import javax.annotation.processing.Filer;
import java.io.*;

/**
 * 有输入和输出，有字节流和字符流
 * 字节输入流 ：InputStream
 * 字节输出流 : OutputStream
 * 字符输入流 : Reader
 * 字符输出流 : Writer
 *
 * 加上File前缀，表示文件流
 *
 * 处理流 ： 在其他流的基础上进行链接，包装，在不改变被包装流的基础之上， 提供更加好用的功能 。
 *
 */
public class IOTest2 {

    @Test
    public void test8() throws UnsupportedEncodingException {
        int n = 0x6211;
        System.out.println((char)n);
        String s1 = "abc我和你qqq"; // 在程序中， 字符串永远是Unicode字符序列。
        // 如果要把字符串保存文件或网络传输，必须要编码。为了省空间和高效通用。

        // 读文件时是解码

        int n2 = 0xCED2;
        System.out.println((char)n2);

        // 编码 ：字符串 => 字节数组， 为了保存字符串
        // 存文件时是编码
        //byte[] bytes1 = s1.getBytes();// 编码, 默认使用的是utf8,和项目设置一致
        byte[] bytes1 = s1.getBytes("utf8"); // 使用指定的编码方式编码
        for (int i = 0; i < bytes1.length; i++) {
            System.out.print(Integer.toHexString(bytes1[i]) + " ");
        }
        System.out.println();

        byte[] bytes2 = s1.getBytes("gbk");
        for (int i = 0; i < bytes2.length; i++) {
            System.out.print(Integer.toHexString(bytes2[i]) + " ");
        }
        System.out.println();

        // 解码 ：字节数组 => 字符串， 为了读取内容转成字符串
        //String ss1 = new String(bytes1); // 使用默认的编码方式解码
        String ss1 = new String(bytes1, "utf8"); // 使用指定的编码方式解码
        System.out.println(ss1);

        String ss2 = new String(bytes2, "gbk"); // 把2个字节的gbk码 查表对应到相应的unicode码
        System.out.println(ss2);
    }

    // 再写一个程序，把这50个随机数读出来。再读中文字符串。
    @Test
    public void exer2() {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream("50个随机数");
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);
            for (int i = 0; i < 50 ;i++){
                int n = ois.readInt();
                System.out.println(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 写一个程序，在文件中写入50个随机的100以内的整数。写成二进制文件
    // 再写一行字符串， 包含一些中文 写成utf8格式
    @Test
    public void exer1() {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("50个随机数");
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            for (int i = 0; i < 50; i++) {
                oos.writeInt((int)(Math.random() * 100));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test7() {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream("二进制文件");
            bis = new BufferedInputStream(fis);
            ois = new ObjectInputStream(bis);

            int i = ois.readInt();
            System.out.println(i);
            boolean b1 = ois.readBoolean();
            boolean b2 = ois.readBoolean();
            System.out.println(b1);
            System.out.println(b2);
            long l = ois.readLong();
            System.out.println(l);
            double v = ois.readDouble();
            System.out.println(v);

            String s = ois.readUTF();
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test6() {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        // 二进制文件中保存的数据通常是内存数据的副本。
        ObjectOutputStream oos = null; // 字节流一定是处理二进制文件。
        try {
            fos = new FileOutputStream("二进制文件");
            bos = new BufferedOutputStream(fos);
            oos = new ObjectOutputStream(bos);
            oos.writeInt(10); // 写4个字节数据
            oos.writeBoolean(true); // 写1个字节数据
            oos.writeBoolean(false);
            oos.writeLong(20); // 写8字节
            oos.writeDouble(3.14);

            oos.writeUTF("abc我和你qqq"); // UTF8格式的字符串
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Test
    public void test5() {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter("使用缓冲流写文本");
            bufferedWriter = new BufferedWriter(fileWriter);
            // 处理数据
            String[] content = {
                                "我是一些内容的字符串1",
                                "我是一些内容的字符串2",
                                "我是一些内容的字符串3",
                                "我是一些内容的字符串4",
                                "我是一些内容的字符串5",
                                "我是一些内容的字符串6",
                                "我是一些内容的字符串7",
                                "我是一些内容的字符串8",
                                "213424982374982374892734234",
                                "falksdflakslskajflkajsdfkljasdf"};
            for (int i = 0; i < content.length; i++) {
                bufferedWriter.write(content[i]);
                bufferedWriter.newLine(); // 写跨平台的换行， 这是最有价值方法***
                /* 使用上面的方法代替
                bufferedWriter.write(13);
                bufferedWriter.write(10);
                */
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test4() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 包装就是对象关联
            fileReader = new FileReader("HashMap.java.bak");
            bufferedReader = new BufferedReader(fileReader);
            String line; // readLine()方法是最有价值方法！！！！！
            while ((line = bufferedReader.readLine()) != null) { // 直接从输入 流中读一行字符串
                System.out.println(line); // line中没有了换行。
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 只需要关闭高级流， 因为在关闭高级流时，会自动地顺带把低级流关闭了
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test3() {
         // 通过输出流写数组
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("使用数组写文件");
            // 写数据，
            String[] content = {
                                "我是一些内容的字符串1",
                                "我是一些内容的字符串2",
                                "我是一些内容的字符串3",
                                "我是一些内容的字符串4",
                                "我是一些内容的字符串5",
                                "我是一些内容的字符串6",
                                "我是一些内容的字符串7",
                                "我是一些内容的字符串8",
                                "213424982374982374892734234",
                                "falksdflakslskajflkajsdfkljasdf"};
            for (int i = 0; i < content.length; i++) {
                char[] array = content[i].toCharArray();
                //fileWriter.write(array); // 一次性写一个数组
                // 第2个参数是数组的开始索引，第3个参数是要写的长度（字符数）
                // 把数组的一部分数据写入到输出流中。 重点方法***
                fileWriter.write(array, 1, array.length - 1);
                fileWriter.write(13); // 回车
                fileWriter.write(10); // 换行
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test2() { // 相当于入口方法。异常尽量捕获
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("./src/com/atguigu/javase/io/FileCopy.java"); // 当前目录
            // 处理数据
            char[] buf = new char[1024]; // 缓冲区
            int n; // 保存每次读到缓冲区中的实际字符数
            // 前几次肯定都是满的，最后一次不满， n <= 数组的长度
            while ((n = fileReader.read(buf)) != -1) { // 只要没有读到末尾， 就一直读取， -1表示读完最后一次再读的情况。
                // 循环中处理已经读的数据
                for (int i = 0; i < n; i++) { // 循环次数以实际读到的个数为准
                    System.out.print(buf[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test1() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("./src/com/atguigu/javase/io/FileCopy.java"); // 当前目录
            // 处理数据
            int ch;
            while ((ch = fileReader.read()) != -1) {
                System.out.print((char)ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
