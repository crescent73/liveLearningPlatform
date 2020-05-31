package com.java;

import com.java.controller.StudentController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class ApplicationTest {
    @Autowired
    private StudentController studentController;
    @Test
    public void contextLoads() {
//        String hello = studentController.helloWorld();
//        System.out.println(hello);
    }

}
