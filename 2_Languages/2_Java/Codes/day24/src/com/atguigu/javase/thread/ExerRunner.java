package com.atguigu.javase.thread;

public class ExerRunner implements Runnable {

    private int count = 500;

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }
    }
}
