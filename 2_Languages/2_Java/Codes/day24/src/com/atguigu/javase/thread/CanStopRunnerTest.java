package com.atguigu.javase.thread;

public class CanStopRunnerTest {

    public static void main(String[] args) {
        Runnable runner = new CanStopRunner();
        Thread thread = new Thread(runner);
        thread.setName("子线程");
        thread.start();

        int n = 0;
        for (int i = 0; i < 1000000; i++) {
            n++;
            ++n;
        }

        //thread.stop(); // 强制停止线程, 非常暴力的停止, 容易出问题.
        ((CanStopRunner)runner).setFlag(false); // 以通知的方式停止
    }
}
