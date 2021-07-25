package com.hw.demo.services.impl;

import com.hw.demo.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Student.class)
@ConditionalOnClass(ProcessServer.class)
public class SchoolConfig {

    @Autowired
    private Student student;

    @Bean(name = "processBean")
    public ProcessServer processBean() {
        ProcessServer processServer = new ProcessServer();
        processServer.setId(student.getId());
        processServer.setName(student.getName());
        processServer.setPwd(student.getPwd());
        return processServer;
    }
}
