package com.atguigu.javae.collection;

import org.junit.Test;

import java.util.*;
/**
 *      Map : 保存一对一对的对象
 *
 *  泛型 ：generic 解决类型安全问题。
 *      在声明和创建集合时， 在类名后面添加<元素的类型>
 *
 *      好处 ：
 *          1） 集合在添加元素时， 不能乱添加， 只能添加泛型类型。
 *          2） 从集合中获取元素时， 因不有了泛型， 获取元素时也不需要造型， 因为集合中的元素数据类型固定了。
 *
 *  能用泛型的地方尽量用泛型
 *
 *  集合遍历 ：
 *      1） 增强for循环（为了统一数组和集合的遍历）
 *          for (元素数据类型 临时变量 ： 集合） {
 *          }
 *
 *     2） 对于List集合， 还支持经典for循环，经典的
 *
 *     3） 迭代器， 集合遍历的真相。
 */

// 第三方比较器
class MyComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof Student && o2 instanceof Student) {
            return ((Student)o1).getGrade() - ((Student)o2).getGrade();
        }
        throw new RuntimeException("对象不可比");
    }
}

public class CollectionTest {

    // 使用泛型创建List集合， 保存随机数， 求平均
    @Test
    public void exer3() {
        List list0 = new ArrayList(); // 集合中可以保存任何对象
        List<Object> list1 = new ArrayList<Object>(); // 集合中可以保存Object及其子类对象

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add((int)(Math.random() * 20));
        }
        System.out.println(list);
        int sum = 0;
        // 遍历集合
        // 1) 获取到迭代器
        Iterator<Integer> iterator = list.iterator();
        // 注意1 ： 获取到迭代器后马上使用。
        // 2) 循环依次询问
        while (iterator.hasNext()) {
            // 3) 如果有元素，真的获取
            Integer next = iterator.next();
            // 注意2 ：next()只能调用一次
            sum += next;
        }
        // 注意3 ： 一次性使用
        double avg = (double)sum / list.size();
        System.out.println("avg = " + avg);
    }


    // 写一个自定义比较器，把Employee对象的比较的时候以age为准
    @Test
    public void exer2() {
        /*
        Comparator com = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Employee) o1).getAge() - ((Employee) o2).getAge();
            }
        };
        Set set = new TreeSet(com);
         */
        Set<Employee> set = new TreeSet<Employee>(new Comparator() {
                                                        @Override
                                                        public int compare(Object o1, Object o2) {
                                                            if (!(o1 instanceof Employee) || !(o2 instanceof Employee)) {
                                                                throw new RuntimeException("对象不可比");
                                                            }
                                                            return ((Employee) o1).getAge() - ((Employee) o2).getAge();
                                                        }
                                                 });
        Employee emp1 = new Employee("张三", 30, 100);
        Employee emp2 = new Employee("李四", 20, 5000);
        Employee emp3 = new Employee("五五", 50, 20);
        set.add(emp1);
        set.add(emp2);
        set.add(emp3);

        for (Employee tmp : set) { // 编译器会把这段代码变成对迭代器的使用。
            System.out.println(tmp);
        }
        System.out.println("**********************************");
        Iterator<Employee> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
    @Test
    public void test7() {
        Student s1 = new Student(1, "小明", 3, 90);
        Student s2 = new Student(2, "小丽", 1, 100);
        Student s3 = new Student(3, "小刚", 5, 70);
        Student s4 = new Student(4, "小花", 4, 100);
        Comparator comparator = new MyComparator();
        // 一旦关联了比较器对象， TreeSet也可以添加普通对象
        // 如果比较器和内部比较能力同时存在， 以比较器为准， 更灵活。
        Set<Student> set = new TreeSet<Student>(comparator); // TreeSet关联了比较器， 内部排序称为定制排序
        set.add(s1);
        set.add(s2);
        set.add(s3);
        set.add(s4);
        set.add(new Student());
        try {
            //set.add("abc");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        for (Object tmp : set) {
            System.out.println(tmp);
        }

        System.out.println("***********************************");

        // 使用迭代器：
        // 1) 先获取迭代器，必须向集合对象要，　迭代器初始指针指向第一个元素之前
        Iterator<Student> iterator = set.iterator();
        // 使用注意事项 1 ： 迭代器必须使用新鲜的, 冒热气的。。。
        //set.add(new Student()); // 任何的修改都会导致迭代器被污染
        // 2) 循环不断询问迭代器是否有下一个元素，　判断当前指针的后面是否有元素
        while (iterator.hasNext()) {
            // 3)　如果有下一个元素，　真的取下元素，并移动指针　
            Student stu = iterator.next();
            // 使用注意事项2 ： next()方法在循环中必须只能调用一次。
            System.out.println(stu);
        }
        // 使用注意事项3 ： 迭代器是一次性使用， 用过即废。
    }

    // 创建TreeSet集合， 添加几个对象， 是否能添加？？？ 如果不能添加，该怎么办？
    @Test
    public void exer1() {
        Set<Employee> set = new TreeSet<Employee>();
        Employee emp1 = new Employee("张三", 30, 100);
        Employee emp2 = new Employee("李四", 20, 5000);
        Employee emp3 = new Employee("五五", 50, 20);
        set.add(emp1);
        set.add(emp2);
        set.add(emp3);

        for (Object tmp : set) {
            System.out.println(tmp);
        }
    }

    @Test
    public void test6() {
        Student s1 = new Student(1, "小明", 3, 90);
        Student s2 = new Student(2, "小丽", 1, 100);
        Student s3 = new Student(3, "小刚", 5, 70);
        Student s4 = new Student(4, "小花", 4, 100);

        Set<Student> set = new TreeSet<Student>();
        set.add(s1);
        set.add(s2);
        set.add(s3);
        set.add(s4);

        for (Object tmp : set) {
            System.out.println(tmp);
        }

        //int abc = s1.compareTo("abc");
        //System.out.println(abc);
    }

    @Test
    public void test5() {
        Set<String> set = new TreeSet<String>();
        set.add("abc");
        set.add("999");
        set.add("汉字");
        set.add("BBB");
        //set.add(500); 不能混放

        System.out.println(set);
    }

    @Test
    public void test4() {
        Set<Integer> set = new TreeSet<Integer>();// 无序不可重复, 内部要实现自然排序
        set.add(50);
        set.add(20);
        set.add(30);
        set.add(10);
        set.add(40);
        set.add(25);
        //set.add("abc"); //因为内部要自然排序， 所以"abc"找不到位置

        for (Object tmp : set) {
            System.out.println(tmp);
        }
        /* 增强for会被编译器变成下面的：
        for (Iterator iterator = set.iterator(); iterator.hasNext(); System.out.println(tmp))
            tmp = iterator.next();
        */
    }

    @Test
    public void test3() {
        Student s1 = new Student(1, "小明", 3, 90);
        Student s2 = new Student(1, "小明", 3, 90);
        Student s3 = new Student(1, "小明", 3, 90);

        Set<Student> set = new HashSet<Student>();
        set.add(s1);
        set.add(s2);
        set.add(s3);

        System.out.println(set);
        set.remove(s1); // 就可以了
        System.out.println(set);
    }

    @Test
    public void test2() {
        //创建Set集合, 保存10个随机数, 再把这些数复制到List集合中
        Set set = new HashSet();
        for (int i = 0; i < 10; i++) {
            set.add((int)(Math.random() * 30));
        }
        System.out.println("set : " + set);
        List list = new ArrayList(set);
        //list.addAll(set); // 直接添加参数集合中的所有元素
        System.out.println("list : " + list);
        // 排序
        Object max = Collections.max(list);
        Collections.sort(list);
        System.out.println("list : " + list);
        System.out.println("max : " + max);
    }

    @Test
    public void test1() {
        //创建Set集合, 保存10个随机数, 再把这些数复制到List集合中
        // Set set = new HashSet(); // Set中能保存什么类型的对象， 都可以。
        Set<Integer> set = new HashSet<Integer>(); // 约束此集合中只能保存Integer对象
        for (int i = 0; i < 10; i++) {
            set.add((int)(Math.random() * 30));
        }
        // set.add("abc"); // set集合此时是类型安全的。只能保存Integer,其他类型一概不能保存。

        System.out.println("set : " + set);
        //List list = new ArrayList(); // List集合中究竟能保存什么？
        List<Integer> list = new ArrayList<Integer>(); // List集合中究竟能保存什么？
        for (Integer obj : set) {
            list.add(obj);
        }
        System.out.println("list : " + list);
        //list.add("abc");
        // 排序
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    // 交换
                    Integer tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
        System.out.println("list : " + list);
        //找出最大值, 对List集合排序.

        // 有了泛型， 获取元素时也不需要造型， 因为集合中的元素数据类型固定了。
        int max = list.get(0); // 假定第一个数最大
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        //int max = list.get(list.size() - 1);
        System.out.println("max : " + max);
    }

}
