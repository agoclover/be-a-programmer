package com.atguigu.javase.homework;

import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {

    public static void main(String[] args) {
        List<String> list = new LinkedList<String>();
        list.add("yy");
        list.add("aa");
        list.add("bb");
        list.add("xx");
        list.add("zz");
        list.add("qq");
        list.add("ee");
        list.add("ff");
        list.add("aa");
        list.add("bb");

        System.out.println(list);

        list.remove("qq");

    }
}
