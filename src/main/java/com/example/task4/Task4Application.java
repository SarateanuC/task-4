package com.example.task4;

import com.example.task4.dbo.StudentDbo;
import com.example.task4.dbo.StudentUniversityDbo;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MappedTypes({StudentDbo.class, StudentUniversityDbo.class})
@MapperScan("com.example.task4.mapper")
@SpringBootApplication
public class Task4Application {

    public static void main(String[] args) {
        SpringApplication.run(Task4Application.class, args);


    }

}
