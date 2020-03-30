package com.java.controller.intf;

import com.java.model.entity.Course;
import com.java.model.vo.ResultData;

public interface AdminController {
    // 查看课程列表
    ResultData courseList(Course course);
    // 课程详情
    ResultData courseDetail(Course course);
    // 修改课程信息
    ResultData modifyCourseInfo(Course course);
    // 删除课程信息
    ResultData deleteCourse(Integer courseId);
}
