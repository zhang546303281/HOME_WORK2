package com.hw.demo.bean;

import java.util.List;

public class Klass {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void print(){
        System.out.println(this.getStudents());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Klass{");
        sb.append("students=").append(students);
        sb.append('}');
        return sb.toString();
    }
}
