package com.java.controller.intf;

import com.java.model.entity.Course;
import com.java.model.vo.ResultData;

public interface StudentController {

    // 获取课程列表（搜索课程）
    ResultData getCourseList(Course course);
    // 获取课程详情
    ResultData getCourseDetail(Course course);

    // 获取收藏课程列表
    ResultData collectCourseList();
    // 添加收藏课程
    ResultData addCollectCourse();
    // 删除收藏课程
    ResultData deleteCollectCourse();

    // 获取直播流
    ResultData getLivingStream();
    // 签到
    ResultData sign();

    // 获取消息列表
    ResultData getMessageList();
    // 发送消息
    ResultData sendMessage();

    // 获取公告列表
    ResultData getNoticeList();

    // 获取作业列表
    ResultData getAssignmentList();
    // 获取作业详情
    ResultData getAssignmentDetail();
    // 提交作业
    ResultData submitAssignment();

    // 获取文档列表
    ResultData getFileList();
    // 下载文档
    ResultData downloadFile();

}
