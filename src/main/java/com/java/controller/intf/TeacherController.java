package com.java.controller.intf;

import com.java.model.entity.Course;
import com.java.model.vo.ResultData;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Map;

public interface TeacherController {
    // 获取课程列表（搜索课程）
    ResultData getCourseList(Course course);
    // 创建课程
    ResultData createCourse();
    // 删除课程
    ResultData deleteCourse();
    // 修改课程信息
//    ResultData modifyCourseInfo();
    // 设置课程权限
    ResultData setCoursePermission();

    // 获取收藏课程列表
    ResultData collectCourseList();
    // 添加收藏课程
    ResultData addCollectCourse();
    // 删除收藏课程
    ResultData deleteCollectCourse();

    // 查看录播列表
    ResultData getVideoList();
    // 查看录播
    ResultData getVideo();
    // 删除录播
    ResultData deleteVideo();

    // 发送直播流
    ResultData sendLivingStream();
    // 发布签到
    ResultData publishSign();
    // 查看签到情况
    ResultData getSignDetail();
    // 获取当前在线人数
    ResultData getOnlinePeople();

    // 获取消息列表
    ResultData getMessageList();
    // 发送消息
    ResultData sendMessage(String message,Map<String,Object> session);

    // 获取公告列表
    ResultData getNoticeList();
    // 发布公告
    ResultData publishNotice();
    // 修改公告
    ResultData modifyNotice();
    // 删除公告
    ResultData deleteNotice();

    // 获取作业列表
    ResultData getAssignmentList();
    // 获取作业详情
    ResultData getAssignmentDetail();
    // 发布作业
    ResultData publishAssignment();
    // 修改作业
    ResultData gradeAssignment();
    // 删除作业
    ResultData deleteAssignment();

    // 上传文档
    ResultData uploadFile();
    // 获取文档列表
    ResultData getFileList();
    // 下载文档
    ResultData downloadFile();
    // 修改文档信息
    ResultData modifyFileInfo();
    // 删除文档
    ResultData deleteFile();
}
