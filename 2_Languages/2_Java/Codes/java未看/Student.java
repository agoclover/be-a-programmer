package com.atguigu.javase.javabean;

public class Student {

    private int id;
    private String name;
    private int grade;
    private double score;

    public Student() {}

    public Student(int id, String name, int grade, double scocre) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.score = scocre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public String toString() {
        return "学号 : " + id + ", 姓名 : " + name + ", 年级 : " + grade + ", 分数 : " + score;
    }

}
