package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.model.entity.Course;
import com.java.model.entity.StudentCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StudentCourseDao extends BaseMapper<StudentCourse> {

    List<Course> searchStudentCourse(Integer studentId, Course course);
}
