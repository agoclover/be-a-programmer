package com.atguigu.javase.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数组 : 一组相同类型的任意数据的组合实现统一管理
 * 集合 : 解决批量对象的存储问题. 可以简单看成一个可变长度的Object[]
 *  1) Collection : 保存一个一个的对象, 无序可重复, 不按添加顺序保存对象, 对象可以重复
 *          boolean add(Object obj) 添加一个新对象, 返回布尔
 *          boolean contains(Object obj) 判断是否包含某个对象
 *          boolean remove(Object obj) 从集合中删除指定对象
 *          int size() 获取集合中的元素个数
 *
 *          Set接口 : 无序不可重复
 *              HashSet : 使用哈希算法实现的Set集合
 *                  去重规则 : 两个对象的equals为true并且两个对象的哈希码也相同.
 *
 *                 equals和hashCode的关系 :
 *                  // 如果两个对象的equals为true, 那么两个对象哈希码必须一样, 体现特征性
 *                  // 如果两个对象的equals为false, 那么两个对象哈希码必须不同, 体现散列性.
 *              TreeSet : 使用二叉树实现的Set集合
 *
 *          List接口 : 有序可重复, 按照添加顺序保存元素, 可以重复, 像数组, 有下标概念
 *              void add(int index, Object ele) 在指定下标处插入新元素
 *              Object get(int index) 获取指定下标处的对象, 使用频率最高
 *              Object remove(int index) 删除指定下标处的对象
 *              Object set(int index, Object ele) 替换指定下标处的元素为新元素
 *
 *              ArrayList : 基于数组实现的List集合.
 *              LinkedList : 基于链表实现的List集合
 *
 *  2) Map : 保存一对一对的对象
 */
public class CollectionTest {

    @Test
    public void test5() {
        String s1 = "abc";
        String s2 = new String("abc");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2)); // 只关注对象的内容, 不关注位置
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());
        String s3 = new String("ccc");
        System.out.println(s2.equals(s3));
        System.out.println(s3.hashCode());
    }

    @Test
    public void test4() {
        Set set = new HashSet(); // 无序不可重复
        Student s1 = new Student(1, "小明", 3, 90);
        Student s2 = new Student(2, "小丽", 2, 100);
        Student s3 = new Student(1, "小明", 3, 90);
        set.add(s1);
        set.add(s2);
        set.add(s3); // 现在能去重, 原因是重写了equals和hashCode
        set.add(s1); // 能去重吗?

        set.add(new Integer(500));
        set.add(new Integer(500));

        System.out.println(s1.equals(s3));
        System.out.println(s1.hashCode());
        System.out.println(s3.hashCode());

        for (Object tmp : set) {
            System.out.println(tmp);
        }
    }

    // 练习 : 创建一个List集合, 保存10个20以内的随机整数, 遍历打印
    @Test
    public void exer3() {
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            int n = (int)(Math.random() * 20);
            list.add(n);
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
    // 练习 : 创建一个List集合, 保存10个20以内的随机整数, 不要重复, 遍历打印
    @Test
    public void exer33() {
        List list = new ArrayList();
        while (list.size() != 10){
            int n = (int)(Math.random() * 20);
            if (!list.contains(n)) {
                list.add(n);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }
    @Test
    public void exer32() {
        List list = new ArrayList();
        for (int i = 0; i < 10; ) {
            int n = (int)(Math.random() * 20);
            if (!list.contains(n)) {
                list.add(n);
                i++;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    @Test
    public void test3() {
        //List list = new List();
        List list = new ArrayList();
        list.add("abc");
        list.add("yyy");
        list.add(new Student(2, "小花", 5, 80));
        list.add(200); // Integer.valueOf(200)
        list.add("abc");
        list.add("yyy");
        list.add(100);
        list.add("yyy");

        // 全部删除
        while (true) {
            boolean b = list.remove("yyy");
            if (!b) {
                break;
            }
        };

        System.out.println(list);
        System.out.println("************************************");
        // 使用经典for可以遍历list
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            System.out.println(o);
        }

    }

    @Test
    public void test2() {
        //List list = new List();
        List list = new ArrayList();
        list.add("abc");
        list.add("yyy");
        list.add(new Student(2, "小花", 5, 80));
        list.add(200); // Integer.valueOf(200)
        list.add("abc");
        list.add("yyy");
        list.add(100);

        System.out.println(list);//[abc, yyy, Student{id=2, name='小花', grade=5, score=80.0}, 200, abc, yyy, 100]
        list.add(2, 3); //[abc, yyy, 3, Student{id=2, name='小花', grade=5, score=80.0}, 200, abc, yyy, 100]
        list.set(3, 1);

        System.out.println(list);

        list.remove(1);

        System.out.println(list);

        list.remove(Integer.valueOf(1)); // 要想删除整数对象, 必须装箱

        System.out.println(list);

        System.out.println(list.contains("zzz"));

        Object o = list.get(4);
        System.out.println(o);

        System.out.println(list.size());
    }

    // 练习 : 创建一个Set集合, 保存10个20以内的随机整数, 遍历打印
    @Test
    public void exer1() {
        Set set = new HashSet();
        for (int i = 0; i < 10; i++) {
            int n = (int)(Math.random() * 20);
            set.add(n);
        }
        // 遍历
        for (Object obj : set) {
            System.out.println(obj);
        }
    }
    // 练习 : 创建一个Set集合, 保存10个20以内的随机整数, 必须够10个, 遍历打印
    @Test
    public void exer23() {
        Set set = new HashSet();
        for (int i = 0; i < 10; ) {
            int n = (int)(Math.random() * 20);
            boolean flag = set.add(n);
            if (flag) { // 有条件迭代
                i++;
            }
        }
        for (Object tmp : set) {
            System.out.println(tmp);
        }
    }
    @Test
    public void exer22() {
        Set set = new HashSet();
        for (int i = 0; i < 10; i++) {
            int n = (int)(Math.random() * 20);
            boolean flag = set.add(n);
            if (!flag) {
                i--;
            }
        }
        for (Object tmp : set) {
            System.out.println(tmp);
        }
    }

    @Test
    public void exer2() {
        Set set = new HashSet();
        while (set.size() != 10) { // 每次循环都检查集合中的真实元素个数.
            int n = (int)(Math.random() * 20);
            set.add(n);
        }
        for (Object tmp : set) {
            System.out.println(tmp);
        }
    }

    @Test
    public void test1() {
        // Set set = new Set(); // 接口不可以创建对象
        Set set = new HashSet(); // 不按添加顺序保存对象
        set.add("abc");
        set.add("yyy");
        set.add(new Student(1, "小明", 5, 80));
        set.add(new Integer(200));
        set.add(100); // set.add(Integer.valueOf(100))
        boolean b1 = set.add("yyy");
        boolean b2 = set.add(200);
        System.out.println(b1 + "," + b2);
        System.out.println(set.size()); // 元素个数
        System.out.println(set);
        boolean contains = set.contains(100); // 装箱了, 判断是否包含
        System.out.println(contains);
        set.remove("abc"); // 删除指定元素
        System.out.println(set);
        System.out.println("**********************************");
        // 遍历集合
        // 增强for循环
        /*
        for (元素类型 临时变量 : 集合名) {
            访问临时变量
        }*/
        for (Object tmp : set) {
            System.out.println(tmp);
        }
    }
}
