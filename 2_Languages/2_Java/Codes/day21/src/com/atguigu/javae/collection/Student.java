package com.atguigu.javae.collection;

import java.util.Objects;

public class Student implements Comparable {

    private int id;
    private String name;
    private int grade;
    private double score;

    public Student() {
    }

    public Student(int id, String name, int grade, double score) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", score=" + score +
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                grade == student.grade &&
                Double.compare(student.score, score) == 0 &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, score);
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Student)) {
            throw new RuntimeException("对象不可比");
        }
        int n = (int)(this.score - ((Student)o).score);
        if (n == 0) {
            n = 1;
        }
        return n;
    }

}
