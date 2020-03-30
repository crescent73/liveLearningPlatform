package com.java.service.intf;

import com.java.model.entity.Assignment;

import java.util.List;

public interface AssignmentService {
    // 获取作业列表
    List<Assignment> getAssignmentList();
    // 获取作业详情
    Assignment getAssignmentDetail();
    // 发布作业
    int publishAssignment();
    // 修改作业
    int gradeAssignment();
    // 删除作业
    int deleteAssignment();
    // 提交作业
    int submitAssignment();
}
