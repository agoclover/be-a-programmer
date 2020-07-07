package com.atguigu.javase.homework;

import org.junit.Test;

import java.util.*;

public class HomeWork {

    @Test
    public void work3() {
        //给定一个字符串, 统计每个字符出现的次数
        String s = "alskdjfalksjdflkasj12489237423uy4234982虽荆防颗粒楞顺朝右 进口中我喹我右你我我我";
        // 思考 ：用一个数组来解决这个问题。
    }

    @Test
    public void work2() {
        //给定一个字符串, 统计每个字符出现的次数
        String s = "alskdjfalksjdflkasj12489237423uy4234982虽荆防颗粒楞顺朝右 进口中我喹我右你我我我";
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            Integer count = map.get(ch); // 字符为键，出现次数为值
            if (count == null) { // 说明ch字符没有向map中put过
                count = 0; // 次数为0
                map.put(ch, 1);
            } else { // put过， 用新值覆盖老值
                map.put(ch, ++count);
            }
        }
        System.out.println(map);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < args.length; i++) {
            list.add(Integer.parseInt(args[i]));
        }
        Collections.sort(list);
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);
    }

    @Test
    public void work1() {
        //2.请把学生名与考试分数录入到Map中，并按分数显示前三名成绩学员的名字。
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("小明", 99);
        map1.put("小丽", 99);
        map1.put("小刚", 100);
        map1.put("小林", 99);
        map1.put("小飞", 100);
        map1.put("小黑", 40);
        map1.put("小花", 99);
        map1.put("小白", 90);
        map1.put("小红", 90);
        map1.put("小紫", 90);

        // 使用TreeMap把数据倒一下
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int n = o2 - o1;
                if (n == 0) { // 防止去重
                    n = 1;
                }
                return n;
            }
        };
        Map<Integer, String> map2 = new TreeMap<Integer, String>(comparator);
        // 遍历map1， 先获取到所有的键对象
        Set<String> keys = map1.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            Integer score = map1.get(name);
            map2.put(score, name);
        }

        Set<Map.Entry<Integer, String>> entries = map2.entrySet();
        int grade = 0; // 级别
        int lastScore = 0;
        for (Map.Entry tmp : entries) {
            if ((Integer)tmp.getKey() != lastScore) {
                grade++;
                if (grade == 4) {
                    break;
                }
            }
            System.out.println(tmp.getKey() + ", " + tmp.getValue());
            lastScore = (Integer)tmp.getKey();
        }
        // 前三名
        Set<Map.Entry<String, Integer>> entries1 = map1.entrySet();

        Iterator<Map.Entry<String, Integer>> iterator1 = entries1.iterator();
        while(iterator1.hasNext()) {
            System.out.println(iterator1.next().getKey());
        }

    }
}
