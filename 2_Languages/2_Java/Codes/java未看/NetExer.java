package com.atguigu.javase.net;

import org.junit.Test;
import sun.awt.windows.WBufferStrategy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class NetExer {

    // 让服务器给客户端发一段文本, 最后加上当前时间
    @Test
    public void server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            // accept会阻塞
            System.out.println("服务器在8888端口监听.....");
            Socket socket1 = serverSocket.accept();
            Callable callable = new Callable() {
                public Integer call() throws Exception{
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream()));
                    bufferedWriter.write("你好客户端, 当前时间 : " + LocalDateTime.now());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    socket1.close();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            };
            new Thread(new FutureTask(callable)).start();
        }

        //serverSocket.close();
    }

    @Test
    public void client() throws IOException {
        Socket socket2 = new Socket("127.0.0.1", 8888);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        String s = bufferedReader.readLine();
        System.out.println(s);

        bufferedReader.close();
        socket2.close();
    }
}
