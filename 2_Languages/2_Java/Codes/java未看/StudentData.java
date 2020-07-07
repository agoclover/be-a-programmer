package com.atguigu.javase.javabean;

import java.util.*;

public class StudentData {

    public static List<Student> getList() {
        List<Student> list = new ArrayList<>();
        String[] names1 = {"李", "王", "赵", "张", "刘", "杨", "曹"};
        String[] names2 = {"刚", "伟", "丽", "娜", "琳", "宁", "旭", "阳", "帅"};
        for (int i = 0; i < 20; i++) {
            int id = i + 1;
            int index1 = (int) (Math.random() * 2000) % names1.length;
            int index2 = (int) (Math.random() * 2000) % names2.length;
            String name = names1[index1] + names2[index2];
            int grade = (int) (Math.random() * 6 + 1); // 随机的1~6
            double score = (int) (Math.random() * 101);
            list.add(new Student(id, name, grade, score));
        }
        // 遍历
        for (Student tmp : list) {
            System.out.println(tmp);
        }
        list.add(new Student(30, "小明", 3, 50));
        list.add(new Student(30, "小明", 3, 50));
        list.add(new Student(30, "小明", 3, 50));
        list.add(new Student(30, "小明", 3, 50));
        list.add(new Student(30, "小明", 3, 50));
        list.add(new Student(30, "小明", 3, 50));
        list.add(new Student(30, "小明", 3, 50));
        System.out.println("****************************************");
        return list;
    }
    public static void main(String[] args) {
        List<Student> list = getList();
        // 找出3年级没有及格的同学,倒序显示前2个....
        // 找3年级同学
        List<Student> list2 = new ArrayList<>();
        for (Student tmp : list) {
            if (tmp.getGrade() == 3) {
                list2.add(tmp);
            }
        }
        // 没有及格的
        List<Student> list3 = new ArrayList<>();
        Iterator<Student> iterator = list2.iterator();
        while (iterator.hasNext()) {
            Student next = iterator.next();
            if (next.getScore() < 60) {
                list3.add(next);
            }
        }

        Comparator<Student> comparator = new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return -(int) (o1.getScore() * 100 - o2.getScore() * 100);
            }
        };
        //排序
        Collections.sort(list3, comparator);

        List<Student> list4 = new ArrayList<>();
        // 取前两个
        for (int i = 0; i < list3.size(); i++) {
            list4.add(list3.get(i));
            if (list4.size() == 2) {
                break;
            }
        }

        // 最终的list4是结果
        System.out.println("最终结果 : ");
        Iterator<Student> iterator1 = list4.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
        }

        // 通过今天的学习, 目标是把这些操作用一行解决!!!
    }
}
