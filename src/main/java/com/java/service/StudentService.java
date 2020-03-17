package com.java.service;

import com.java.bean.Student;

import java.util.List;

public interface StudentService {

    List<Student> searchStudent();

    List<Student> findStudent();
}
