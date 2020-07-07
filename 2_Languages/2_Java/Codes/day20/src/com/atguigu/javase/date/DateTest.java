package com.atguigu.javase.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Date可以用来表示日期,也包括时间了.
 *      缺点是创建对象极其不方便, 因为它处理日期时间有基础值
 *
 * Calendar 日历, 内部使用一个大的int[]来保存所有数据
 *      访问数据时必须要提供下标, 下标值用常量来表示
 *      缺点 : 月份存储小1, 读取或设置属性都不方便.内容是可以改变的.
 *
 * java8中的新API,  所有数据存储都是真实的, 内容不可改变, 所有的修改都会产生新对象.
 * LocalDate 处理日期
 * LocalTime 处理时间
 * LocalDateTime 处理日期时间
 * DateTimeFormatter 格式化
 */

public class DateTest {

    @Test
    public void test5() {
        // 直接创建奥运会
        LocalDate date1 = LocalDate.of(2008, 8, 8);
        System.out.println(date1);
        LocalTime time = LocalTime.now();
        System.out.println(time);
        LocalDateTime datetime = LocalDateTime.now();
        System.out.println(datetime);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String format = simpleDateFormat.format(datetime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//new DateTimeFormatter();
        String format = dtf.format(datetime);
        System.out.println(format);
    }

    // 练习 : 创建一个LocalDate对象, 把它变成你的生日, 获取你的百日. 打印输出
    @Test
    public void exer3() {
        LocalDate date1 = LocalDate.now();
        DayOfWeek dayOfWeek = date1.getDayOfWeek(); // 星期
        System.out.println(dayOfWeek);
        int dayOfYear = date1.getDayOfYear(); // 年中的天
        System.out.println(dayOfYear);

        LocalDate date2 = date1.withYear(1978).withMonth(6).withDayOfMonth(9);
        System.out.println(date2);

        LocalDate date3 = date2.plusDays(100);
        System.out.println(date3);
    }

    @Test
    public void test4() {
        // java8新的API
        //LocalDate localDate = new LocalDate();
        LocalDate date1 = LocalDate.now();
        System.out.println(date1);
        int year = date1.getYear(); // 获取年
        int month = date1.getMonthValue();
        int day = date1.getDayOfMonth();
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        LocalDate date2 = date1.withYear(2008).withMonth(8).withDayOfMonth(8); // 伴随着修改产生新对象
        System.out.println(date2);
        LocalDate date3 = date2.plusMonths(10);// 10月以后
        System.out.println(date3);
    }

    // 练习 : 创建一个Calendar对象, 把它变成你的生日, 获取你的百日. 打印输出
    @Test
    public void exer2() {
        Calendar calendar = Calendar.getInstance();
        // 1978-6-9
        calendar.set(Calendar.YEAR, 1978);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        calendar.add(Calendar.DAY_OF_MONTH, 100);
        System.out.println(simpleDateFormat.format(calendar.getTime()));
    }

    @Test
    public void test3() {
        Calendar calendar = Calendar.getInstance(); //new Calendar(); 类似工厂的方式获取对象
        System.out.println(calendar);
        //calendar.getYear();
        int year = calendar.get(Calendar.YEAR);// 通用的get.要想获取什么属性, 通过参数指定
        System.out.println(year);
        int month = calendar.get(Calendar.MONTH); // 内部存储的数据比实际的月小1
        System.out.println(month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(day);
        //calendar.setYear(2008);
        // 设置它为奥运动 2008-08-08
        calendar.set(Calendar.YEAR, 2008); // 设置年
        calendar.set(Calendar.MONTH, 7); // 8月
        calendar.set(Calendar.DAY_OF_MONTH, 8);
        System.out.println(calendar.getTime()); // 获取相应的Date对象
        calendar.add(Calendar.MONTH, 10); // 奥运会10个月后
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -100); // 奥运会10个月后又100天 前
        System.out.println(calendar.getTime());
    }

    @Test
    public void test2() {
        // 年是以1900为基础. 月是以1为基础, 日是以0为基础
        Date date = new Date(2008, 8, 8);
        System.out.println(date); // 3908-09-08
        int year = date.getYear();
        System.out.println(year);
        int month = date.getMonth();
        System.out.println(month);
    }

    @Test
    public void test1() {
        long millis = System.currentTimeMillis(); // 以1970-1-1 0:0:0.0000
        System.out.println(millis); // 时间
        Date date = new Date();
        System.out.println(date);
        // 使用日期格式化器
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date); // 把日期对象格式化成字符串, 并要符合模式
        System.out.println(format);

        String string = "2008-08-08 11:22:30"; // 格式和模式完全匹配
        try {
            Date date2 = sdf.parse(string); // 字符串解析成日期对象
            System.out.println(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String format1 = sdf.format(millis); // 也可以格式化毫秒
        System.out.println(format1);
    }
}
