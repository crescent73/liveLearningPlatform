package com.java.controller;

import com.java.bean.Student;
import com.java.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/hello")
    public String helloWorld(){
        return "hello world\n from student controller!";
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
