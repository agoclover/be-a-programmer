package com.atguigu.javase.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

class Person {
    int id;
    String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

public class MapTest {

    @Test
    public void test2() {
        HashSet set = new HashSet();
        Person p1 = new Person(1001,"AA");
        Person p2 = new Person(1002,"BB");
        set.add(p1);
        set.add(p2);
        p1.name = "CC";
        p1 = null;
        set.remove(p1); // 能否删除p1?
        System.out.println(set);
        set.add(new Person(1001,"CC")); // 能否添加？
        System.out.println(set);
        set.add(new Person(1001,"AA"));
        System.out.println(set);

    }
    @Test
    public void work3() {
        //给定一个字符串, 统计每个字符出现的次数
        String s = "alskdjfalksjdflkasj12489237423uy4234982虽荆防颗粒楞顺朝右 进口中我喹我右你我我我";
        // 思考 ：用一个数组来解决这个问题。
        int[] arr = new int[65536];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i)]++;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                System.out.println((char)i + " : " + arr[i]);
            }
        }
    }


    @Test
    public void test1() {
        // 类型推断， 编译器会根据左面推断右面的泛型类型
        Map<Integer, String> map = new HashMap<>(); // 右面的泛型一定和左面的泛型一致
        map.put(9, "nine");
        map.put(3, "three");
        map.put(2, "two");
        map.put(8, "eight");
        map.put(7, "seven");
        map.put(10, "ten");
        map.put(20, "20"); // 下标4
        map.put(30, "30"); // 下标是14

        map.put(25, "25"); // 会和下标9冲突
        map.put(41, "41");
        map.put(57, "57");

        map.put(100, "100"); // 12

        map.put(200, "200"); // 扩容并重新散列

        System.out.println(map);
    }
}
