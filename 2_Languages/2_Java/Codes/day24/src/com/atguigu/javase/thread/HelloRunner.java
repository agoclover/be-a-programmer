package com.atguigu.javase.thread;

// 练习 : 创建一个子线程，在线程中输出1-100之间的偶数，主线程输出1-100之间的奇数。

// 1) 写一个具体类, 实现Runnable接口, 并实现run方法,这个方法就是线程体(入口)
public class HelloRunner implements Runnable {

    private int n;

    @Override
    public void run() {
        for (n = 0; n < 500; n++) {
            System.out.println(Thread.currentThread().getName() + " : " + n);
        }
    }
}
