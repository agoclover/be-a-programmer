package com.atguigu.javase.thread;

public class CanStopRunner implements Runnable {

    //在main方法中创建并启动1个线程。线程循环随机打印100以内的整数，直到主线程从键盘读取了“Q”命令。
    private int count = 0;
    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public void run() {
        while (flag) {
            System.out.println(Thread.currentThread().getName() + " : " + count++);
            if (count == 200) {
                count = 0;
            }
        }
        System.out.println("循环结束, 我是关键代码");
    }
}
