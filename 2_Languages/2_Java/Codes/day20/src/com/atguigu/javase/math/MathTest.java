package com.atguigu.javase.math;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MathTest {

    @Test
    public void test3() {
        // 定点数
        BigDecimal bd1 = new BigDecimal("23984923472342348239492384234.293847239874293874289374982374928374928374982374982734982734982734");
        BigDecimal bd2 = new BigDecimal("239849234723234234234234242348239492384234.2932424234233423423847239874293874289374982374928374928374982374982734982734982734");

        BigDecimal multiply = bd1.multiply(bd2);
        System.out.println(multiply);
    }

    @Test
    public void test2() {
        BigInteger bi1 = new BigInteger("2398472984234238472985734598673987249857293487239847298374293874982374982374982374982734897234234");
        BigInteger bi2 = new BigInteger("9999999999992984234238472985734598673987249857293487239847298374293874982374982374982374982734897234234");

        BigInteger add = bi1.add(bi2);
        System.out.println(add);

        BigInteger multiply = bi1.multiply(bi2);
        System.out.println(multiply);

    }

    @Test
    public void test1() {
        int rand = (int)(Math.random() * 100);
        System.out.println(rand);

        double d = 3.5;
        int n = (int)d;
        System.out.println(n);

        System.out.println(Math.round(3.5));
        System.out.println(Math.round(-3.5)); // ?
        System.out.println(Math.round(-3.6)); // ?
    }
}
