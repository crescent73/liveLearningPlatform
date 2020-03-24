package com.java.service.impl;

import com.java.model.entity.Student;
import com.java.dao.StudentDao;
import com.java.service.intf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> searchStudent() {
        return studentDao.searchStudent();
    }

    @Override
    public List<Student> findStudent() {
        return studentDao.findStudent();
    }
}
