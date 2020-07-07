package com.atguigu.javase.net;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  IP : Internet Protocol 网络协议, 用于在网络中定位唯一的主机
 *  port : 本质是一个整数, 用于标识某个主机的上的一个进程.
 *
 *  IP + port 形成一个Socket, 是一个管道.
 *
 *  在IP和端口基础之上. 需要进一步细化如何通信
 *      1) TCP (传输控制协议) 稳定可靠, 像打电话
 *          使用TCP协议前，须先建立TCP连接，形成传输数据通道
         *  传输前，采用“三次握手”方式，是可靠的
         *  TCP协议进行通信的两个应用进程：客户端、服务端
         *  在连接中可进行大数据量的传输
         *  传输完毕，需释放已建立的连接，效率低
 *
 *       服务器端和客户端之分
 *
 *      2) UDP (用户数据报) 高效, 容易丢数据
 *         将数据、源、目的封装成数据包，不需要建立连接
         * 每个数据报的大小限制在64K内
         * 因无需连接，故是不可靠的
         * 发送数据结束时无需释放资源，速度快
 *
 */
public class NetTest {

    // 从客户端发送文件给服务端，服务端保存到本地。并返回“发送成功”给客户端。并关闭相应的连接。
    // File1 -------> Client ---------> Server --------> File2
    //         fis            nos  nis            fos
    //                       <---------
    //                     nis(字符)nos(字符)
    @Test
    public void server2() throws Exception {
        ServerSocket serverSocket = new ServerSocket(7777);
        Socket socket1 = serverSocket.accept();
        InputStream nis = socket1.getInputStream();
        FileOutputStream fos = new FileOutputStream("girl2.jpg");
        BufferedWriter nos = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));

        byte[] buf = new byte[8192];
        int n;
        while ((n = nis.read(buf)) != -1) {
            fos.write(buf, 0, n);
        }

        nos.write("发送成功");
        nos.newLine();
        nos.flush();

        // 关闭所有资源
        nos.close();
        fos.close();
        nis.close();
        socket1.close();
        serverSocket.close();
    }

    @Test
    public void client2() throws Exception {
        Socket socket2 = new Socket("127.0.0.1", 7777);
        FileInputStream fis = new FileInputStream("girl.jpg");
        OutputStream nos = socket2.getOutputStream();
        BufferedReader nis = new BufferedReader(new InputStreamReader(socket2.getInputStream()));

        // 发送文件, 读文件为准
        byte[] buf = new byte[8192];
        int n;
        while ((n = fis.read(buf)) != -1) {
            nos.write(buf, 0, n);
        }
        // 这里需要给服务器一个停止信号
        socket2.shutdownOutput();

        String s = nis.readLine();
        System.out.println(s);

        nis.close();
        nos.close();
        fis.close();
        socket2.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocket server = new ServerSocket(9999);// 在本机绑定端口9999
        Socket socket1 = server.accept();// 接受请求, 准备接受客户端请求,.此方法会阻塞
        System.out.println(socket1);
        InputStream inputStream = socket1.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);
        bufferedReader.close();
        socket1.close();
        server.close();
    }
    @Test
    public void client() throws IOException {
        Socket socket2 = new Socket("localhost", 9999);
        System.out.println(socket2);
        OutputStream outputStream = socket2.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("你好服务器, 我是客户端... 请指教");
        bufferedWriter.newLine(); // 写入换行
        bufferedWriter.flush(); // flush作用是把数据刷入网线
        bufferedWriter.close();
        socket2.close();
    }
}
