package com.java.dao;

import com.java.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentDao {

    @Select("select * from student")
    List<Student> searchStudent();

    List<Student> findStudent();
}
