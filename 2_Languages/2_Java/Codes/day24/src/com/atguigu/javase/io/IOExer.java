package com.atguigu.javase.io;

import java.io.*;

// 使用键盘输入流从键盘获取一些内容,把这些内容保存成一个文件key.txt,文件使用gbk编码
// 直到从键盘输入 "exit" 命令为止
public class IOExer {

    public static void main(String[] args) {
        InputStream in = System.in;
        InputStreamReader isr = null;
        BufferedReader bufReader = null;

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bufWriter = null;

        try {
            isr = new InputStreamReader(in);
            bufReader = new BufferedReader(isr);

            fos = new FileOutputStream("key.txt");
            osw = new OutputStreamWriter(fos, "gbk");
            bufWriter = new BufferedWriter(osw);

            String line;
            while ((line = bufReader.readLine()) != null) {
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                bufWriter.write(line);
                bufWriter.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufWriter != null) {
                try {
                    bufWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
