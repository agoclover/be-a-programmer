package com.atguigu.javae.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型 ：
 * 1. 解决元素存储的安全性问题
 * 2. 解决获取数据元素时，需要类型强转的问题
 */

/**
 * 自定义泛型类
 */
class Person<X> { // <X>就是表示泛型, X是某种类型，具体 是什么，不知道，不重要。像一个形参
    // 泛型参数。它在创建对象时才能真的确定具体类型。泛型参数是和对象相关的。不和类相关
    // 如果在创建对象时没有指定泛型类型， 默认是Object类型，所以它完全兼容Object

    private String name;
    private X info;

    public Person() {}

    public Person(String name, X info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public X getInfo() {
        return info;
    }

    public void setInfo(X info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", info=" + info +
                '}';
    }
}



public class GenericTest {

    @Test
    public void test3() {
        // 创建对象时的泛型必须具体
        Person<Integer> p1 = new Person<Integer>("张三", 30); // 类型安全
        Integer info1 = p1.getInfo();

        Person<String> p2 = new Person<String>("李四", "女");
        String info2 = p2.getInfo();

        Person p3 = new Person("王五", true); // 类型不安全
        Object info3 = p3.getInfo();

    }

    @Test
    public void test2() {
        Person p1 = new Person("张三", "男");
        Person p2 = new Person("李四", 40);
        Object info1 = p1.getInfo();
        Object info2 = p2.getInfo();
        new Person("王五", true);
    }

    @Test
    public void test1() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add((int)(Math.random() * 20));
        }
        //list.add("agvc"); // 泛型约束不能乱加对象
        System.out.println(list);
        Integer integer = list.get(0);
        System.out.println(integer);

        List<Double> list2 = new ArrayList<Double>();
        list2.add(3.22);
        //list2.add(5); // list2.add(Integer.valueOf(5));
        list2.add((double)5);
    }
}
