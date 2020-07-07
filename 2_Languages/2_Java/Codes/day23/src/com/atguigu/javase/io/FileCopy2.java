package com.atguigu.javase.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy2 {

    // 处理任意文件的复制。
    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //fis = new FileInputStream("进行曲.flac");
            fis = new FileInputStream("note/HashMap.java");
            fos = new FileOutputStream("hashMap.java.bak2");
            byte[] buf = new byte[8192]; // 8K效率最高
            int n;
            while ((n = fis.read(buf)) != -1) {
                fos.write(buf, 0, n);
            };
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
