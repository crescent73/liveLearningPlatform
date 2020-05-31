package com.java.service.intf;

import com.java.model.entity.Course;
import com.java.model.vo.ResultData;

public interface AdminService {
    //增加课程
    public ResultData addCourse(Course course);

    //删除课程
    public ResultData deleteCourse(Integer id);
}
