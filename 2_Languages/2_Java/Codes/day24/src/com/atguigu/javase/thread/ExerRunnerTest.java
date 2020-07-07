package com.atguigu.javase.thread;

public class ExerRunnerTest {

    // 练习 : 创建两个子线程，让其中一个输出1-100之间的偶数，另一个输出1-100之间的奇数。
    public static void main(String[] args) {
        Runnable runner = new ExerRunner();
        Thread thread = new Thread(runner);
        thread.setName("子线程");
        thread.start();

        //Thread.currentThread().setName("主线程");

        Runnable runner2 = new ExerRunner2();
        Thread thread2 = new Thread(runner2);
        thread2.setName("子线程2");
        thread2.start();
    }
}
