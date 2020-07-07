package com.atguigu.javase.io;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * 字节, 字符, 输入, 输出
 * InputStream OutputStream Reader Writer
 * 文件流
 * FileInputStream FileOutputStream FileReader FileWriter
 * 缓冲区
 * BufferedInputStream BufferedOutputStream BufferedReader BufferedWriter
 * 对象流(字节流)
 * ObjectInputStream ObjectOutputStream
 *
 * 复制文件时使用缓冲流反而慢, 用节点流快
 *
 * 对象序列化 : 把对象在GC区中的数据写入输出流 ObjectOutputStream writeObject
 *      要想序列化的对象的类必须实现Serializable接口
 *      静态属性和transient修饰的成员不能被序列化.
 * 反序列化 : 把输入流中的数据还原成对象
 *
 * 编码 : 字符串 => 字节数组
 * 解码 : 字节数组 => 字符串
 * 转换流 :
 *      InputStreamReader 字节流->字符流,  解码
 *          在转换时可以指定编码方式
 *      OutputStreamWriter 字节流->字符流, 编码
 *         在转换时可以指定编码方式
 *
 * System.in 是一个输入流
 * System.out 是打印流, 也是输出流, 但是它可以自动flush
 *
 * 重点流 : FileInputStream, FileOutputStream, BufferedReader, BufferedWriter
 *         InputStreamReader, OutputStreamWriter, ObjectInputStream, ObjectOutputStream
 */
public class IOTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 和迭代器一样
        while (scanner.hasNextLine()) { // 以行为单位
            if (scanner.hasNextInt()) {
                int n = scanner.nextInt();
                System.out.println("整数: " + n);
            } else if (scanner.hasNextDouble()) {
                double d = scanner.nextDouble();
                System.out.println("浮点数 : " + d);
            } else {
                String next = scanner.nextLine();
                System.out.println(next);
            }
        }
    }


    public static void main2(String[] args) {
        InputStream in = System.in;// 对应的是键盘
        InputStreamReader isr = null;
        BufferedReader buf = null;
        try {
            isr = new InputStreamReader(in);
            buf = new BufferedReader(isr);
            String line;
            while ((line = buf.readLine()) != null) {
                if (line.equals("exit")) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void test24() {
        PrintStream out = System.out;
        out.println("abc");
        out.flush();
        out.close();

        out.println("xxx");
    }

    // 读取ArrayList.java文件, 写成另一个文件ArrayList_utf8.java
    @Test
    public void test23() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader buf = null;

        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bufw = null;

        try {
            fis = new FileInputStream("ArrayList.java");
            isr = new InputStreamReader(fis, "gbk"); // 指定编码方式
            buf = new BufferedReader(isr);

            fos = new FileOutputStream("ArrayList_utf8.java");
            osw = new OutputStreamWriter(fos, "utf8");
            bufw = new BufferedWriter(osw);

            String s;
            while ((s = buf.readLine()) != null) {
                System.out.println(s);
                bufw.write(s);
                bufw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bufw != null) {
                try {
                    bufw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test21() {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter buf = null;
        try {
            fos = new FileOutputStream("转换流写文本");
            //osw = new OutputStreamWriter(fos); // 转换时以默认编码方式
            osw = new OutputStreamWriter(fos, "gbk"); // 转换时以gbk编码
            buf = new BufferedWriter(osw);

            buf.write("alsdkjfalksjfdlkajsdf");
            buf.newLine();
            buf.write("来一些汉字");
            buf.newLine();
            buf.write("134234234234234");
            buf.newLine();
            buf.write("来一些汉字2");
            buf.newLine();
            buf.write("来一些汉字3");
            buf.newLine();
            buf.write("来一些汉字4");
            buf.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test20() {
        //FileReader fileReader = null; // 这个类太烂了, 不能处理其他编码
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader buf = null;
        try {
            fis = new FileInputStream("ArrayList.java");
            //isr = new InputStreamReader(fis); // 使用的还是项目默认编码方式
            isr = new InputStreamReader(fis, "gbk"); // 指定编码方式
            buf = new BufferedReader(isr);
            String s;
            while ((s = buf.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test11() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream("对象序列化");
            ois = new ObjectInputStream(fis);
            /*
            Object obj1 = ois.readObject();
            Object obj2 = ois.readObject();
            Object obj3 = ois.readObject();
            System.out.println(obj1);
            System.out.println(obj2);
            System.out.println(obj3);
            System.out.println(Student.school);
             */
            /*
            Student[] arr = (Student[])ois.readObject();
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }*/

            List<Student> list = (List<Student>)ois.readObject();
            Iterator<Student> iterator = list.iterator();
            while (iterator.hasNext()) {
                Student next = iterator.next();
                System.out.println(next);
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

    @Test
    public void test10() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("对象序列化");
            oos = new ObjectOutputStream(fos);

            Student s1 = new Student(1, "小明", 3, 50);
            Student s2 = new Student(2, "小丽", 2, 100);
            Student s3 = new Student(3, "小花", 4, 80);
            s3.school = "尚硅谷";
            //oos.writeObject(s1);
            //oos.writeObject(s2);
            //oos.writeObject(s3);

            /*
            Student[] arr = {s1, s2, s3};
            oos.writeObject(arr); // 数组也可以序列化..
             */

            List<Student> list = new ArrayList<Student>();
            list.add(s1);
            list.add(s2);
            list.add(s3);
            oos.writeObject(list);
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
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream("二进制");
            ois = new ObjectInputStream(fis);
            /*
            int b1 = ois.read();
            int b2 = ois.read();
            int b3 = ois.read();
            int b4 = ois.read();
            System.out.println(b1);
            System.out.println(b2);
            System.out.println(b3);
            System.out.println(b4);
            int rsult = (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
            System.out.println(rsult);
             */
            int i = ois.readInt();
            System.out.println(i);
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
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("二进制");
            oos = new ObjectOutputStream(fos);

            oos.writeInt(2348923);
            //oos.write(255); // 看成byte就是-1
            //oos.write(-1);

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
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream("随机数");
            ois = new ObjectInputStream(fis);
            // 不知道有多少个随机数
            int count = ois.readInt();
            for (int i = 0; i < count; i++) {
                int i1 = ois.readInt();
                System.out.println(i1);
            }
            System.out.println("*****************************");
            int count2 = ois.readInt();
            for (int i = 0; i < count2; i++) {
                long l = ois.readLong();
                System.out.println(l);
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

    @Test
    public void test4() {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("随机数");
            oos = new ObjectOutputStream(fos);
            int rand = (int)(Math.random() * 100);
            oos.writeInt(rand); // 先写整数个数
            for (int i = 0; i < rand; i++) { // 实际再写整数
                oos.writeInt((int)(Math.random() * 100));
            }
            int rand2 = (int)(Math.random() * 100);
            oos.writeInt(rand2); // 写long个数
            for (int i = 0; i < rand2; i++) {
                oos.writeLong((int)(Math.random() * 10000));
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
    public void test3() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("test2.txt");
            fileWriter.write("qalksdfalksdjflaskjdf");
            fileWriter.write(10);
            fileWriter.write("名师回蓝 国右中中");
            //fileWriter.flush(); // 把OS缓冲区中的数据刷入硬盘
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close(); // close前会自动调用flush
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void exer3() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("HashMap.java"));
        // 如果不加第2个参数, 文件 的打开方式是清空, 第2个参数如果是true, 以追加的方式打开.
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("HashMap.java", true));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            //bufferedWriter.flush();
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

    @Test
    public void exer2() { // 使用缓冲流反而慢, 用节点流快
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        long l1 = System.currentTimeMillis();
        try {

            fis = new FileInputStream("day24.zip");
            bis = new BufferedInputStream(fis);
            fos = new FileOutputStream("test2.zip");
            bos = new BufferedOutputStream(fos);
            byte[] buffer = new byte[8192];
            int n;
            while ((n = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, n);// 实际读了n个, 写n个字节
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long l2 = System.currentTimeMillis();
        System.out.println("使用BufferedInputStream : " + (l2 - l1));
    }

    @Test
    public void exer1() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        long l1 = System.currentTimeMillis();
        try {
            fis = new FileInputStream("day24.zip");
            fos = new FileOutputStream("test3.zip");
            byte[] buffer = new byte[8192];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);// 实际读了n个, 写n个字节
            }
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
        long l2 = System.currentTimeMillis();
        System.out.println("使用inputStream : " + (l2 - l1));
    }

    @Test
    public void test2() {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader("HashMap.java");
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
    public void test1() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("ArrayList.java");
            /*
            int ch;
            while ((ch = fileReader.read()) != -1) { // 只能一次读一个字符
                System.out.print((char)ch);
            }*/
            char[] buf = new char[1024];
            int n; // n的作用是从输入流中一次性读到数组中后,实际读了多少
            while ((n = fileReader.read(buf)) !=-1) {
                // 处理数据时, 只处理n个
                for (int i = 0; i < n; i++) {
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
}
