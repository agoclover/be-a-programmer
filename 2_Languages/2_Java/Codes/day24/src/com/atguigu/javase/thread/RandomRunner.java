package com.atguigu.javase.thread;

public class RandomRunner implements Runnable {

    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag) {
            int n = (int)(Math.random() * 100);
            System.out.println(Thread.currentThread().getName() + " : " + n);
        }
        System.out.println("我要死了....");
    }
}
