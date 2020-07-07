package com.atguigu.javase.thread;

public class ExerRunner2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            if (i % 2 != 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
}
