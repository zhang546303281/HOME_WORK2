package com.hw.demo.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mime")
@Component
public class Student {

    public Student() {
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    private int id;

    private String name;

    private String pwd;

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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", pwd='").append(pwd).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
