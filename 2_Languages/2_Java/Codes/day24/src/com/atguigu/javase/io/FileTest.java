package com.atguigu.javase.io;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileTest {

    // 获取某个目录的大小
    public long dirSize(File file) {
        System.out.println("统计目录 : " + file);
        long size = 0;
        File[] files = file.listFiles();
        if (files == null) { // 如果目录没有访问权限,会返回 null
            return size;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                size += files[i].length();
            } else if (files[i].isDirectory()){
                size += dirSize(files[i]); // 子目录必须也得重新统计, 递归调用
            }
        }
        return size;
    }

    // 统计C:/windows目录的大小
    @Test
    public void test3() {
        File file = new File("C:/windows");
        System.out.println("size : " + dirSize(file));
        //System.out.println("大小 : " + file.length()); // 如果目录不空, 返回非0, 不是目录大小
        //File file2 = new File("qq");
        //System.out.println(file2.length()); // 如果目录为空, 长度为0
    }



    @Test
    public void test2() {
        File file = new File("aa/bb/cc/dd/ee/ff");
        //file.createNewFile(); // 创建文件
        file.mkdirs(); // 创建多层目录

        File[] files = file.listFiles();// 列出file对象代表的目录中的所有内容,包括子目录和子文件
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                System.out.println("目录 : " + files[i]);
            } else {
                System.out.println("文件 : " + files[i] + "长度 : " + files[i].length());
                System.out.println(files[i].isHidden());
            }
        }
    }

    @Test
    public void test1() throws IOException {
        File file =  new File("HashMap.java");
        System.out.println(file);
        System.out.println("file.length() : " + file.length()); // 单位是字节
        System.out.println("file.getAbsolutePath() : " + file.getAbsolutePath());
        System.out.println("file.canRead() : " + file.canRead());
        System.out.println("file.canWrite() : " + file.canWrite());
        //System.out.println("file.createNewFile() : " + file.createNewFile());
        System.out.println("file.exists() : " + file.exists());
        System.out.println("file.getFreeSpace() : " + file.getFreeSpace());
        System.out.println("file.getTotalSpace() : " + file.getTotalSpace());
        System.out.println("file.isDirectory() : " + file.isDirectory());
        System.out.println("file.isFile() : " + file.isFile());
        System.out.println("file.lastModified() : " + file.lastModified());
        System.out.println("file.delete() : " + file.delete());
    }
}
