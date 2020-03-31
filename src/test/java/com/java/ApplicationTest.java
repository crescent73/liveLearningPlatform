package com.java;

import com.java.MainApplication;
import com.java.controller.impl.StudentControllerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class ApplicationTest {
    @Autowired
    private StudentControllerImpl studentController;
    @Test
    public void contextLoads() {
        String hello = studentController.helloWorld();
        System.out.println(hello);
    }

}
