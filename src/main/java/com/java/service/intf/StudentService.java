package com.java.service.intf;

import com.java.model.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> searchStudent();

    List<Student> findStudent();
}
