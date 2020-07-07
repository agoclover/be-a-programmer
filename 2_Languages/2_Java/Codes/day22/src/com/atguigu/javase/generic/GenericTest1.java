package com.atguigu.javase.generic;

import com.atguigu.javase.Student;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 泛型  ： 要解决类型的安全问题， 如果使用Object类型会带来类型的损失。
 * 典型的应用就是在集合中， 集合中理论上可以保存任意对象，实际上我们应该让它泛型化
 * 集合类<元素类型>, 添加元素只能添加指定类型，获取元素时一定能获取指定类型的对象，不需要造型。
 */

// 自定义泛型类， Y泛型类型就可以在本类中的任意位置使用
class Person<Y> { // Y表示这个类的类型参数， 是一个变化的类型， 表示某种类型
    // 此时并不知道具体类型，在创建此类对象时就可以知道了。泛型类型是和对象相关的。

    private String name;
    //private Object info; // info数据类型不安全， 不确定
    private Y info; // info数据类型泛型化

    public Person() {
    }

    public Person(String name, Y info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Y getInfo() {
        return info;
    }

    public void setInfo(Y info) {
        this.info = info;
    }

    // 静态环境中不可以使用泛型类型
    /*
    public static void test(Y y) {
    }*/
}

class GenericMethod<A> { // A是成员泛型

    public void test() {}

    // 泛型方法， 在返回值之前加<泛型类型参数>, 必须给一个泛型类型的参数
    // 方法在调用时由实参给定的类型来决定泛型类型, 是某次调用相关
    public static <B> B test2(B b2) { // B是局部泛型参数，类型不确定。
        B b = b2;
        return b;
    }

    /*
    public void test3(B b) { // 不可以跨方法使用局部泛型
    }
    */
}

// 泛型和继承的关系
class Base<T> {
    private T field;
    public void setField(T field) {
        this.field = field;
    }
    public T getField() {
        return field;
    }
}
// 子类在继承有泛型的父类时写法

// 不推荐这种写法， 泛型未知。
class Sub1 extends Base {} // 子类没有理会父类的泛型, 父类的泛型被子类继承后固定是Object

// 推荐这样的方式， 泛型是固定的，不会变，缺点是子类需要多写几个。
class Sub21 extends Base<String>{} // 子类在继承父类时， 把泛型写死，子类继承的泛型类型是固定的。
class Sub22 extends Base<Integer>{} // 这种写法常见，子类中泛型写死，泛型类型就固定，使用子类方便。

// 这种写法最灵活， 因为子类的泛型也不是固定。
class Sub3<A> extends Base<A> {} // 子类中继承泛型， 子类也灵活

public class GenericTest1 {

    public Comparable max(Collection<? extends Comparable> collection) {
        Iterator<? extends Comparable> iterator = collection.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        Comparable max = iterator.next(); // 假设第一个元素最大
        while (iterator.hasNext()) {
            Comparable next = iterator.next();
            if (next.compareTo(max) > 0) {
                max = next;
            }
        }
        return max;
    }

    @Test
    public void test10() {
        List<Integer> list1 = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list1.add((int) (Math.random() * 20));
        }
        System.out.println(list1);
        System.out.println(max(list1));
        List<Double> list2 = new ArrayList<Double>();
        for (int i = 0; i < 10; i++) {
            list2.add((Math.random() * 20));
        }
        System.out.println(list2);
        System.out.println(max(list2));
        Set<Integer> set1 = new HashSet<Integer>();
        for (int i = 0; i < 10; i++) {
            set1.add((int)(Math.random() *30));
        }
        System.out.println(set1);
        System.out.println(max(set1));

        Set<String> set2 = new HashSet<String>();
        set2.add("aaa");
        set2.add("cc");
        set2.add("113");
        set2.add("qqq");
        set2.add("32084923");
        set2.add("汉字");
        set2.add("QQQ");
        System.out.println(max(set2));

    }

    // 参数中的集合可以接收Number子类类型为泛型的集合，并且是只读访问
    public Number avg(List<? extends Number> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).doubleValue(); // Number类型对象
        }
        return sum / list.size();
    }

    @Test
    public void test9() {
        List<Integer> list1 = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list1.add((int) (Math.random() * 20));
        }
        System.out.println(list1);
        System.out.println(avg(list1));// 求集合的平均值
        List<Double> list2 = new ArrayList<Double>();
        for (int i = 0; i < 10; i++) {
            list2.add((Math.random() * 20));
        }
        System.out.println(list2);
        System.out.println(avg(list2));// 求集合的平均值
    }

    @Test
    public void test8() {
        // 有限的通配符 ? 表示未知 super表示父类
        // Number及其未知父类类型， 此类型一定可以兼容Number
        // 下限是Number, 上限未知。
        // <? super Number> 适用于添加元素，但是不适合取元素
        List<? super Number> list1 = null;
        // 可以添加元素，但是只能添加Number及其子类。
        list1.add(200); // 200可以被Number及未知父类兼容
        list1.add(3.22);
        //list1.add(new Object()); Object对象不能添加，因为它是已知父类
        Object object = list1.get(0);

        // Number及其未知子类类型
        // 上限是Number下限未知
        // <? extends Number> 不适用于添加元素, 适用于获取元素
        List<? extends Number> list2 = null;
        //list2.add(200); // 200是已知子类， 不能添加
        //list2.add(3.22); // 3.22也是已知子类，集合要求的是未知子类
        Number number = list2.get(0);

    }

    public void printList(List<?> list) { // 遍历集合
        //list.add(300); 只读访问list集合
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i);
            System.out.println(object + " ");
        }
        System.out.println();
    }

    @Test
    public void test7() {
        List<Integer> list1 = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list1.add((int) (Math.random() * 20));
        }
        printList(list1);

        List<String> list2 = new ArrayList<String>();
        list2.add("asldkf");
        list2.add("tttt");
        list2.add("qqkf");
        list2.add("1234234ldkf");
        list2.add("8u8888");
        printList(list2);

    }

    @Test
    public void test6() {
        //List<Number> list = new ArrayList<Integer>();
        // 左面的list可以保存Number及其子类对象, 如果添加Double是可以的
        // 右面的List只能保存Integer， 但是右面实际不允许
        // 所以泛型不能直接多态
        List<Integer> list1 = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list1.add((int)(Math.random() * 20));
        }
        System.out.println(list1);
        // ?是泛型通配符， 表示类型未知。
        List<?> list2 = list1; // ?表示未知。
        // list2中保存未知类型的对象
        //list2.add(200); // 不能添加， 因为200是已知类型
        //list2.add("abc") 添加时类型确定的不能添加
        list2.add(null); // 因为null类型不确定。
        Object obj = list2.get(0); // 可以获取元素，但是都是Object类型
        List<?> list3 = new ArrayList<Object>();
    }

    @Test
    public void test5() {
        Person<Student> p3 = new Person<>("张三", new Student());

        Sub3<Student> studentSub3 = new Sub3<>();
        studentSub3.setField(new Student());
        Student field = studentSub3.getField();
    }

    @Test
    public void test4() {
        Sub1 sub1 = new Sub1();
        Object field1 = sub1.getField();
        Sub21 sub2 = new Sub21();
        String field2 = sub2.getField();
        Sub22 sub22 = new Sub22();
        Integer field3 = sub22.getField();

        Sub3<Double> doubleSub3 = new Sub3<Double>();
        Double field4 = doubleSub3.getField();
        Sub3<Boolean> booleanSub3 = new Sub3<Boolean>();
        Boolean field5 = booleanSub3.getField();
    }

    // 写一个类， 包含一个泛型方法，在测试类中调用此方法，察看返回值类型
    @Test
    public void test3() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add((int)(Math.random() * 20));
        }
        System.out.println(list);
        // 集合变成数组
        //Object[] objects = list.toArray(); // 为了兼容保留的老方法
        // 如果参数中传入的数组对象可以用，那它就要用， 如果不能用，再创建新的
        Integer[] arr = new Integer[22];
        Integer[] integers = list.toArray(arr); // 典型的泛型方法
        System.out.println(arr);
        System.out.println(integers);
        System.out.println(integers.length);
        // 数组是空的也没有问题，因为它的唯一作用就是让方法感知泛型类型是什么即可
        for (int i = 0; i < integers.length; i++) {
            System.out.println(integers[i]);
        }
    }

    @Test
    public void test2() {
        GenericMethod genericMethod = new GenericMethod();
        String rt = genericMethod.test2("abc"); // 泛型方法一定有参数，用于确定泛型类型
        Integer rt2 = genericMethod.test2(200);
        Object rt3 = genericMethod.test2(null); // 如果是泛型方法， 不要传null, 因为null无法让方法感知类型
    }

    @Test
    public void test1() {
        Person<Integer> p1 = new Person<Integer>("张三", 30);
        Integer info1 = p1.getInfo();

        Person<Double> p2 = new Person<Double>("李四", 5000.0);
        Double info2 = p2.getInfo();

        Person p3 = new Person("王五", "男");
        Object info3 = p3.getInfo();
    }
}
