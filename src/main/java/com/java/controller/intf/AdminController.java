package com.java.controller.intf;

import com.java.model.entity.Course;
import com.java.model.vo.ResultData;

public interface AdminController {
    // 查看课程列表
    ResultData courseList();
    // 课程详情
    ResultData courseDetail();
    // 修改课程信息
    ResultData modifyCourseInfo();
    // 删除课程信息
    ResultData deleteCourse();
}
