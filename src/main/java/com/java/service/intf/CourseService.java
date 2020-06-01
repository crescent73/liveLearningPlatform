package com.java.service.intf;

import com.java.model.entity.Course;
import com.java.model.entity.CourseDetail;
import com.java.model.entity.CourseSchedule;

import java.util.List;

public interface CourseService {
    // 获取课程列表（搜索课程）
    List<Course> getCourseList(Course course);
    // 创建课程
    int createCourse(Course course);
    // 删除课程
    int deleteCourse(List<Integer> courseList);
    // 设置课程权限
    int setCoursePermission(Course course);
    // 开始上课
    int startCourse(CourseSchedule courseSchedule);
    // 下课
    int endCourse(CourseSchedule courseSchedule);
    // 查询学生课表
    List<Course> getStudentCourse(Integer studentId, Course course);
    // 根据课程id查询课程
    Course getCourseBySchedule(Integer courseScheduleId);
    //管理员查询课程
    List<CourseDetail> getCourse(Course course);
}
