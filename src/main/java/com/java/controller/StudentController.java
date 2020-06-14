package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.*;
import com.java.model.vo.ResultData;
import com.java.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private CourseController courseController;
    @Autowired
    private CollectionController collectionController;
    @Autowired
    private SignController signController;
    @Autowired
    private NoticeController noticeController;
    @Autowired
    private AssignmentController assignmentController;
    @Autowired
    private FileController fileController;
    @Autowired
    private CommentController commentController;
    @Autowired
    private CourseService courseService;

    @RequestMapping("/searchCourse")
    public ResultData getCourseList(Course course, Integer studentId) {
        return courseController.getStudentCourseList(studentId,course);
    }

    @RequestMapping("/getCollectionCourseList")
    public ResultData collectCourseList(Course course, Integer userId) {
        return collectionController.collectCourseList(course,userId);
    }

    @RequestMapping("/addCollectCourse")
    public ResultData addCollectCourse(Integer courseId, Integer userId) {
        return collectionController.addCollectCourse(courseId,userId);
    }

    @RequestMapping("/deleteCollectCourse")
    public ResultData deleteCollectCourse(Integer[] collectionId) {
        return collectionController.deleteCollectCourse(collectionId);
    }

    @RequestMapping("/getCourseVideoLIst")
    public ResultData getVideoList() {
        return null;
    }

    @RequestMapping("/getCourseVideo")
    public ResultData getVideo() {
        return null;
    }


    @RequestMapping("/getLivingStream")
    public ResultData getLivingStream(Integer courseScheduleId) {
        ResultData <Map> resultData = new ResultData <>();
        if (courseScheduleId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        Course course = courseService.getCourseBySchedule(courseScheduleId);
        String key=course.getTeacherId()+"-"+courseScheduleId;
        resultData.setCode("200");
        resultData.setMsg("请在此地址拉取直播流：");
        Map<String,String> map = new HashMap<>();
        map.put("src","rtmp://106.15.251.188:1935/live/"+key);
        resultData.setData(map);
        return resultData;
    }

    @RequestMapping("/sign")
    public ResultData sign(Integer signId, Integer studentId) {
        return signController.sign(signId,studentId);
    }

    @RequestMapping("/getNoticeList")
    public ResultData getNoticeList(Notice notice) {
        return noticeController.getNoticeList(notice);
    }


    @RequestMapping("/getAssignmentList")
    public ResultData getAssignmentList(Assignment assignment) {
        return assignmentController.getAssignmentList(assignment);
    }

    @RequestMapping("/getAssignmentDetail")
    public ResultData getAssignmentListDetail(Assignment assignment) {
        return assignmentController.getAssignmentListDetail(assignment);
    }

    @RequestMapping("/submitAssignment")
    public ResultData submitAssignment(StudentAssignment studentAssignment){
        return assignmentController.submitAssignment(studentAssignment);
    }


    @RequestMapping("/searchFile")
    public ResultData searchFile(CourseFile file) {
        return fileController.searchFile(file);
    }

    @RequestMapping("/downloadFile")
    public ResponseEntity downloadFile(CourseFile file) throws UnsupportedEncodingException {
        return fileController.downloadFile(file);
    }

    @RequestMapping("/getCommentList")
    public ResultData getCommentList(Integer courseId) {
        return commentController.getCommentList(courseId);
    }

    @RequestMapping("/addComment")
    public ResultData addComment(Comment comment){
        return commentController.addComment(comment);
    }

    @RequestMapping("/deleteComment")
    public ResultData deleteComment(Integer commentId) {
        return commentController.deleteComment(commentId);
    }

}
