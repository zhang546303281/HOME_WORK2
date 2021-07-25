package com.hw.demo.services.impl;

import com.hw.demo.bean.Klass;
import com.hw.demo.bean.Student;
import com.hw.demo.services.ISchool;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;

@Service("school")
public class School implements ISchool, Serializable, BeanNameAware, ApplicationContextAware {

    private String schoolId;

    private ApplicationContext applicationContext;

    @Override
    public void offer() {
        Klass klass = new Klass();
        klass.setStudents(new ArrayList<>());
        klass.getStudents().add(new Student(101, "S101"));

        System.out.println("school offer:" + klass.toString() + ",school id:" + schoolId);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String s) {
        schoolId = s;
    }
}
