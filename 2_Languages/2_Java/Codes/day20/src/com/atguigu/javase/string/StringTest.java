package com.atguigu.javase.string;

import org.junit.Test;

/**                0      7    12    15
 *   String str = "abcdefg12345我是汉字";
    ####public int length() 获取字符串长度 ：str.length() => 16
    ####public char charAt(int index) 获取指定下标处的字符 ： str.charAt(8) => '2'
    * public char[] toCharArray() 获取字符串的字符数组形式，获取到的是字符串的内部数组的副本
    * public boolean equals(Object anObject) 比较字符串内容
 *   public boolean equalsIgnoreCase(String other); 忽略大小写比较内容
    * public int compareTo(String anotherString) 比较大小
 *
    #####public int indexOf(String s)  搜索参数中的子串s在当前串中的首次出现的下标值
 *          str.indexOf("123") => 7
    * public int indexOf(String s ,int startpoint) 从指定的开始位置搜索参数中的子串s在当前串中的首次出现的下标值
    * public int lastIndexOf(String s)从右向左搜索参数中的子串s在当前串中的首次出现的下标值
    * public int lastIndexOf(String s ,int startpoint)
 *
    * public boolean startsWith(String prefix) 判断是否以子串为开始
    * public boolean endsWith(String suffix)判断是否以子串为开始
 *
    #####public String substring(int start,int end) 取子串, 以start为开始索引(包含) 以end为结束索引(不包含)
    * public String substring(int startpoint) 从start取到末尾
 *
    * public String replace(char oldChar,char newChar) 替换全部的oldChar为newChar
    * public String replaceAll(String old,String new)
 *
    * public String trim() 去除首尾空白字符
    * public String concat(String str) 连接
    * public String toUpperCase() 变成大写
    * public String toLowerCase() 变成小写
    * public String[] split(String regex) 切割
 *
 * String : 字符串
 *      内容不可改变的Unicode字符序列(charsequence), 内部使用char[],但是不能添加删除, 任何的修改都会产生新对象
 * StringBuffer : 字符串
 *      内容可以改变的Unicode字符序列, 内部仍然使用char[]保存字符序列, 可以添加和删除的, 对它的修改不会产生新对象
 *
 *      StringBuffer append(...) 可以在当前串后面追加任意数据
 *      StringBuffer insert(int index, ....) 在指定下标处插入新内容
 *      StringBuffer delete(int begin, int end) 删除一个区间
 *      StringBuffer setCharAt(int index, char ch)
 * StringBuilder是最新的替代StringBuffer, 速度更快, 线程不安全
 * StringBuffer 是老的API, 速度慢, 线程安全.
 *
 */
public class StringTest {

    @Test
    public void test8() {
        String text = "";
        long startTime = 0L;
        long endTime = 0L;
        StringBuffer buffer = new StringBuffer("");
        StringBuilder builder = new StringBuilder("");
        startTime = System.currentTimeMillis();
        for(int i = 0;i<20000;i++){
            buffer.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer的执行时间："+(endTime-startTime));
        startTime = System.currentTimeMillis();
        for(int i = 0;i<20000;i++){
            builder.append(String.valueOf(i));}
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder的执行时间："+(endTime-startTime));
        startTime = System.currentTimeMillis();
        for(int i = 0;i<20000;i++){
            text = text + i;}
        endTime = System.currentTimeMillis();
        System.out.println("String的执行时间："+(endTime-startTime));

    }

    // 声明3个字符串, 以第1个字符串为实参创建StringBuffer对象, 把第2个串串接到后面, 把第3个串插入到最前面.
    @Test
    public void exer() {
        String s1 = "abcde";
        String s2 = "我是汉字";
        String s3 = "23984234";
        StringBuffer stringBuffer = new StringBuffer(s1);
        stringBuffer.append(s2).insert(0, s3);
        System.out.println(stringBuffer);
    }

    @Test
    public void test7() {
        StringBuilder stringBuilder = new StringBuilder(); // ""
        stringBuilder.append("abc").append(200).append(false)
                     .append(3.22).append(' ').append('X')
                     .insert(3, "我是汉字")
                     .insert(0, true).delete(0, 4)
                     .setCharAt(3, '你');
        System.out.println(stringBuilder);
    }

    @Test
    public void test6() {
        StringBuilder stringBuilder = new StringBuilder(); // ""
        stringBuilder.append("abc");
        stringBuilder.append(200);// "abc200"
        stringBuilder.append(false); // "abc200false"
        stringBuilder.append(3.22); // "abc200false3.22"
        stringBuilder.append(' ');
        stringBuilder.append('X'); // 第17个
        stringBuilder.insert(3, "我是汉字"); // "abc我是汉字200false3.22 "
        stringBuilder.insert(0, true); // "trueabc我是汉字200false3.22 "

        stringBuilder.delete(0, 4); // "abc我是汉字200false3.22 "
        stringBuilder.setCharAt(3, '你');
        System.out.println(stringBuilder);
    }


    @Test
    public void test5() {
        String s1 = "abc";
        String s2 = "ABC";
        System.out.println(s1.equals(s2));
        System.out.println(s1.equalsIgnoreCase(s2));
        System.out.println(s1.toUpperCase().equals(s2.toUpperCase()));
    }

    /**
     *
     4.获取两个字符串中最大相同子串。比如：
     str1 = "abcwerthelloyuiodef ";str2 = "cvhellobnm"
     提示：将短的那个串进行长度依次递减的子串与较长
     的串比较。
     */
    public String getMaxSameSubstring(String str1, String str2) {
        // 最大相同子串的长度 控制起来， 初始长度就是极端情况， 是短串的长度
        int ruler = str2.length();
        while (ruler > 0) {
            // 用当前长度从短串中取出所有子串， 在长串中进行测试， 看长串是否包含它，
            int begin = 0; // 取子串的开始索引
            int end; // 取子串的结束索引
            while ((end = begin + ruler) <= str2.length()) { // 从短串中取所有可能的子串
                String substring = str2.substring(begin, end); // substring需要2个参数，1个是开始索引， 另一个是结束索引
                // 如果长串包含子串，任务达成。
                if (str1.indexOf(substring) != -1) {
                    // 总任务达成
                    return (substring);
                }
                begin++; // 右移开始的索引
            }
            // 否则减短这个长度
            ruler--;
        }
        return "";
    }

    @Test
    public void test4() {
        String str1 = "abcwerthelloyuiodef ";
        String str2 = "cvhellobnm";
        System.out.println(getMaxSameSubstring(str1, str2));
    }

    /**
     * String s = "   abc yyy zz我是汉字 我也是汉字     ";
     * 模拟一个trim方法，去除字符串两端的空格。
     * 需要定位开始的非空白字符的下标和最后一个非空白字符的下标.
     */
    @Test
    public void test3() {
        String s = "             ";
        int beginIndex = 0;
        int endIndex = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > 32) {
                beginIndex = i;
                break;
            }
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) > 32) {
                endIndex = i;
                break;
            }
        }
        String substring = s.substring(beginIndex, endIndex + 1);
        System.out.println(substring);
    }

    @Test
    public void test2() {
        //在cmd中输入 path, 把path环境变量复制成字符串, 切割, 分析你的path中有哪些目录
        String path = "C:\\Program Files\\Java\\jdk1.8.0_241\\bin;c:\\app\\pauliuyou\\product\\11.2.0\\dbhome_1\\bin;C:\\WINDOWS\\system32;C:\\WINDOWS;C:\\WINDOWS\\System32\\Wbem;C:\\WINDOWS\\System32\\WindowsPowerShell\\v1.0\\;C:\\WINDOWS\\System32\\OpenSSH\\;C:\\MyProgram\\_MyBin;C:\\Users\\pauliuyou\\AppData\\Local\\Microsoft\\WindowsApps";
        String[] split = path.split(";");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
    }


    @Test
    public void test1() {
        Integer i = new Integer(1);
        Integer j = new Integer(1);
        System.out.println(i == j); // false

        Integer m = 1; // 自动装箱， 直接从缓冲区对象数组中取(范围是-128~127）
        Integer n = 1;
        System.out.println(m == n);

        Integer x = 128; // 缓冲区对象数组不包括它， 所以它是new出来的
        Integer y = 128;
        System.out.println(x == y);
        System.out.println(128 == y); // ? 自动拆箱， 最终比的是两个基本数据值
    }
}
