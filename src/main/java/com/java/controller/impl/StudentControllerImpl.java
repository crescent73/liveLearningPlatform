package com.java.controller.impl;

import com.java.model.entity.Student;
import com.java.service.intf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentControllerImpl {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/hello")
    public String helloWorld(){
        return "hello world!!!\n from student controller!";
    }

    @RequestMapping("/searchStudent")
    public List<Student> searchStudent(){
        return studentService.searchStudent();
    }

    @RequestMapping("/findStudent")
    public List<Student> findStudent(){
        return studentService.findStudent();
    }
}
