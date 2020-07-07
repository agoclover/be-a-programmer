package com.atguigu.javase.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RandomRunnerTest {

    public static void main(String[] args) {
        Runnable runner = new RandomRunner();
        Thread thread = new Thread(runner);
        thread.setName("子线程");
        thread.start();

        /*
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("q")) {
                ((RandomRunner)runner).setFlag(false);
                break; // 自我了结
            }
        }*/

        InputStream in = System.in;
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;
        try {
            isr = new InputStreamReader(in);
            bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equalsIgnoreCase("q")) {
                    ((RandomRunner)runner).setFlag(false);
                    break; // 自我了结
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
