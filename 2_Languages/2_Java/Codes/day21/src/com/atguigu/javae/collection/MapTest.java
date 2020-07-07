package com.atguigu.javae.collection;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Map<K, V> : 保存的一对一对的具有映射关系的对象。 像一个词典
 *      可以简单地看作是两个子集合的组合。
 *      也可看作是特殊的Entry对象的Set集合。
 *
 *  V put(K key, V value) 放入map集合中一对映射。 相当于写词条
 *  V get(K key) 根据键对象获取对应的值对象， 相当于查词典
 *  V remove(K key) 删除给定的键， 并同时删除值
 *  int size() 获取条目个数
 *  Set keySet(); 获取保存所有键 对象的Set子集合
 *  Set entrySet() 获取保存所有条目对象的Set集合
 *
 *
 *  实现子类 ：
 *      HashMap 使用哈希算法实现的Map集合， 线程不安全， 效率高。
 *      TreeMap 基于二叉搜索树实现的Map集合
 *      Hashtable 和HashMap一模一样， 是古老的， 线程安全，效率低
 *          Properties 属性处理器 键是属性名， 值是属性值， 默认所有键值都是字符串
 *
 */
public class MapTest {

    @Test
    public  void test3() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add((int)(Math.random() * 20));
        }
        System.out.println(list);
        Collections.sort(list); // 归并排序
        System.out.println(list);
        Collections.reverse(list); // 反转
        System.out.println(list);
        Collections.shuffle(list); // 乱序
        System.out.println(list);
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 1; i < 51; i++) {
            int area = (int)(Math.PI * i * i);
            map.put(i, area);
        }
        Iterator<Map.Entry<Integer, Integer>> iterator2 = map.entrySet().iterator();
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }
    }

    @Test
    public void test2() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("test.properties")); // 自动处理文件中的有键值对

        String url = properties.getProperty("url"); // 根据属性名获取属性值
        System.out.println(url);

        String password = properties.getProperty("password");
        System.out.println(password);

        properties.setProperty("hello", "world");
        properties.setProperty("path", "c:/windows");

        FileOutputStream fileOutputStream = new FileOutputStream("test.properties");
        properties.store(fileOutputStream, "comment");
    }

    /*编写程序，在main方法中创建Map集合（使用泛型），用来存放圆的半径（key）和面积（value）；
        以半径为key，面积为value，将半径1-50的圆面积数据(直接取整)保存其中；
        将Map中的半径数据取至Set集合中；
        遍历Set集合的半径，逐一从Map中取出对应的面积值，并将半径和面积打印出来。*/
    @Test
    public void exer1() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 1; i < 51; i++) {
            int area = (int)(Math.PI * i * i);
            map.put(i, area);
        }
        // 遍历map
        Set<Integer> set = map.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            Integer value = map.get(next);
            System.out.println(next + " >>>>>>>>>>>>>>>>>>> " + value);
        }
    }

    @Test
    public void test1() {
        Map<Integer, String> map = new HashMap<Integer, String>(); // 空词典
        map.put(9, "nine"); // 键对象是无序不可重复的。
        map.put(2, "two"); // 如果没有老值， 返回null
        map.put(3, "three");
        map.put(8, "eight");
        map.put(4, "four");
        String str = map.put(2, "TWO"); // 相同键put时， 会有新值覆盖老值的过程, 再返回老值
        System.out.println(str);
        String str2 = map.put(0, "zero");
        System.out.println(str2);

        System.out.println("size : " + map.size());
        String value = map.get(8);
        System.out.println(value);
        System.out.println(map);
        map.remove(0);
        System.out.println(map);
        System.out.println("*************************************");
        // Map集合的遍历。
        // 获取保存所有键对象的Set子集合
        Set<Integer> set1 = map.keySet();
        Iterator<Integer> iterator = set1.iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            String val = map.get(key);
            System.out.println(key + " >>>>>>>>>>>>>>>>>>>>>>>> " + val);
        }
        System.out.println("*************************************");
        // 获取保存所有”条目“对象的Set集合。
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        for (Map.Entry<Integer,String> tmp : entries) {
            System.out.println(tmp.getKey() + "<<<<<<<<<<<<<<<<<<<" + tmp.getValue());
        }


    }
}
