package com.java.service.intf;

import com.java.model.entity.Course;

import java.util.List;

public interface CourseService {
    // 获取课程列表（搜索课程）
    List<Course> getCourseList(Course course);
    // 获取课程详情
    Course getCourseDetail(Course course);
    // 创建课程
    int createCourse();
    // 删除课程
    int deleteCourse();
    // 修改课程信息
    int modifyCourseInfo();
    // 设置课程权限
    int setCoursePermission();
}
