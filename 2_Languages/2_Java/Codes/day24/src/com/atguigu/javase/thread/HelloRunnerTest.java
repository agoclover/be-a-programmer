package com.atguigu.javase.thread;

public class HelloRunnerTest {

    // 1 创建一个子线程，在线程中输出1-100之间的偶数，主线程输出1-100之间的奇数。
    public static void main(String[] args) {
        // 2) 创建这个具体类对象, 并以此对象为实参, 创建Thread线程对象
        Runnable runner = new HelloRunner();

        Thread thread1 = new Thread(runner); // 新建一个栈
        thread1.setName("子线程1");
        // 3) 调用Thread线程对象的start方法启动线程.
        //thread1.run(); 普通方法调用
        thread1.start(); // 启动子线程后,马上返回, 激活栈, 并把run方法压入栈底

        Thread thread2 = new Thread(runner); // 使用同一个Runnable, 新建一个栈
        thread2.setName("子线程2");
        thread2.start(); // 激活栈, 并把run方法压入栈底.

        Thread thread3 = Thread.currentThread(); // 感知当前正在执行方法在哪个栈中, 隶属于的线程对象
        thread3.setName("主线程");
        for (int i = 0; i < 500; i++) {
            System.out.println(thread3.getName() + " : " + i);
        }
    }
}
