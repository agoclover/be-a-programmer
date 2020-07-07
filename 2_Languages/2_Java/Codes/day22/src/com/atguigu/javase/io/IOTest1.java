package com.atguigu.javase.io;

import org.junit.Test;

import javax.annotation.processing.Filer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *  流 -- 数据从一个节点到另一个节点的流动.
 *  输入流, 输出流 按方向
 *  字节流, 字符流 按流中数据单位
 *  字节输入流 InputStream
 *  字节输出流 OutputStream
 *  字符输入流 Reader
 *  字符输出流 Writer
 *
 *  文件流 : File前缀
 *      FileReader 读文本文件
 *      FileWriter 写文本文件
 *
 *      FileInputStream 读二进制文件
 *      FileOutputStream 写二进制文件
 *
 *  文件读写步骤 :
 *      1) 创建流对象建立通道
 *      2) 通过通道处理数据
 *      3) 关闭流对象
 */
public class IOTest1 {

    @Test
    public void test4() {
        FileWriter fileWriter = null;
        // ctrl + alt + t
        try {
            fileWriter = new FileWriter("写一个文本文件.txt");
            fileWriter.write('我');
            fileWriter.write('和');
            fileWriter.write('你');
            fileWriter.write(13);
            fileWriter.write(10);
            fileWriter.write('a');
            fileWriter.write('b');
            fileWriter.write('c');
            fileWriter.write(13);
            fileWriter.write(10);
            fileWriter.write('1');
            fileWriter.write('2');
            fileWriter.write('3');
            fileWriter.write('4');
            fileWriter.write('\r');
            fileWriter.write('\n');
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test3() {
        // 1 声明引用, 赋值为null
        FileReader fileReader = null;
        // 2 try catch finally
        try {
            // 5) 在try中创建流对象
            fileReader = new FileReader("一个文件");
            // 6) 处理数据
            int ch = 0;
            while ((ch = fileReader.read()) != -1) {
                System.out.print((char)ch);
            }
        } catch (Exception e) {
            // 4 在catch中处理异常
            e.printStackTrace();
        } finally {
            // 3 在finally中关闭流
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }




    }

    @Test
    public void test2() {
        FileReader fr = null;
        try {
            fr = new FileReader("文本文件名22"); // 当前目录下建文件
            // 2) 通过通道处理数据
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch); // 处理数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 3) 关闭流对象
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test1() throws IOException {
        // 1) 创建流对象建立管子
        FileReader fr = new FileReader("文本文件名"); // 当前目录下建文件
        // 2) 通过通道处理数据
        int ch;
        while ((ch = fr.read()) != -1){
            System.out.print((char)ch); // 处理数据
        }
        // 3) 关闭流对象
        fr.close();
    }
}
