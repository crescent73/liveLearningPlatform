package com.java.service.intf;

import com.java.model.entity.Assignment;
import com.java.model.entity.StudentAssignment;

import java.util.List;

public interface AssignmentService {
    // 获取作业列表
    List<Assignment> getAssignmentList(Assignment assignment);
    // 获取作业详情
    Assignment getAssignmentDetail(Assignment assignment);
    // 发布作业
    int publishAssignment(Assignment assignment);
    // 修改作业
    int gradeAssignment(StudentAssignment studentAssignment);
    // 删除作业
    int deleteAssignment(Assignment assignment);
    // 提交作业
    int submitAssignment(StudentAssignment studentAssignment);
}
