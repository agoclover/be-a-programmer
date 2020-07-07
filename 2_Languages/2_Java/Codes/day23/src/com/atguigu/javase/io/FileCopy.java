package com.atguigu.javase.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCopy {

    // 只能复制文本文件
    public static void main(String[] args) {
        //写一个类FileCopy, 在主方法中,把FileCopy.java文件复制为FileCopy.java.bak文件.
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            //fileReader = new FileReader("./src/com/atguigu/javase/io/FileCopy.java"); // 当前目录
            fileReader = new FileReader("进行曲.flac"); // 当前目录
            fileWriter = new FileWriter("行进曲.flac");
            // 处理数据
            /* 这是落后的方式
            int ch;
            while ((ch = fileReader.read()) != -1) {
                fileWriter.write(ch);
            }*/
            // 使用缓冲区的方式复制文件
            char[] buf = new char[1024];
            int n;
            while ((n = fileReader.read(buf)) != -1) {
                fileWriter.write(buf, 0, n);
            };
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

            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
