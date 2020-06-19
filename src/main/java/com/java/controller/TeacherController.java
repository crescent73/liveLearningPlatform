package com.java.controller;


import com.java.constant.enums.ResultCodeEnum;

import com.java.model.entity.*;
import com.java.model.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/teacher")
public class TeacherController {

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

    @RequestMapping("/searchCourse")
    public ResultData getCourseList(Course course) {
        return courseController.getCourseList(course);
    }

    @RequestMapping(value = "/getCourseImage")
    public ResponseEntity getImage(Integer courseId) throws UnsupportedEncodingException {
        return courseController.getCourseImage(courseId);
    }

    @RequestMapping("/addCourse")
    public ResultData createCourse(Course course,@RequestParam("picture") MultipartFile picture, HttpServletRequest req) {
        return courseController.createCourse(course,picture,req);
    }

    @RequestMapping("/deleteCourse")
    public ResultData deleteCourse(Integer[] courseList) {
        return courseController.deleteCourse(courseList);
    }

    @RequestMapping("/startCourse")
    public ResultData startCourse(Integer courseId) {
        return courseController.startCourse(courseId);
    }

    @RequestMapping("/endCourse")
    public ResultData endCourse(Integer courseScheduleId) {
        return courseController.endCourse(courseScheduleId);
    }

    @RequestMapping("/setCoursePermission")
    public ResultData setCoursePermission(Integer id, Integer courseType) {
        return courseController.setCoursePermission(id,courseType);
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

    @RequestMapping("/getCourseVideoList")
    public ResultData getVideoList() {
        return null;
    }

    @RequestMapping("/getCourseVideo")
    public ResultData getVideo() {
        return null;
    }

    @RequestMapping("/deleteCourseVideo")
    public ResultData deleteVideo() {
        return null;
    }

    /**
     * 发布直播视频流
     * @return 返回两个内容：
     * 1. 服务器地址 server--rtmp://106.15.251.188:1935/live
     * 2. 串流密钥 key-- teacher-courseId
     */
    @RequestMapping("/sendLivingStream")
    public ResultData sendLivingStream(Integer courseScheduleId, Integer teacherId) {
        ResultData <Map> resultData = new ResultData <>();
        if (courseScheduleId==null||teacherId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 检查teacherId
        // 检查 courseScheduleId
        resultData.setCode("200");
        resultData.setMsg("请将直播流推送到如下地址：");
        Map<String,String> map = new HashMap<>();
        map.put("server","rtmp://106.15.251.188:1935/live");
        map.put("key",teacherId+"-"+courseScheduleId);
        resultData.setData(map);
        return resultData;
    }

    @RequestMapping("/publishSign")
    public ResultData publishSign(Integer courseId, Integer teacherId, Integer courseScheduleId) {
        return signController.publishSign(courseId,teacherId,courseScheduleId);
    }

    @RequestMapping("/getSignList")
    public ResultData getSignList(Integer courseId, Integer teacherId, Integer courseScheduleId) {
        return signController.getSignList(courseId,teacherId,courseScheduleId);
    }

    @RequestMapping("/getSignDetail")
    public ResultData getSignDetail(Integer signId) {
        return signController.getSignDetail(signId);
    }


    @RequestMapping("/getNoticeList")
    public ResultData getNoticeList(Notice notice) {
        return noticeController.getNoticeList(notice);
    }


    @RequestMapping("/addNotice")
    public ResultData addNotice(Notice notice) {
        return noticeController.addNotice(notice);
    }

    @RequestMapping("/modifyNotice")
    public ResultData modifyNotice(Notice notice) {
        return noticeController.modifyNotice(notice);
    }

    @RequestMapping("/deleteNotice")
    public ResultData deleteNotice(Notice notice) {
        return noticeController.deleteNotice(notice);
    }

    @RequestMapping("/getAssignmentList")
    public ResultData getAssignmentList(Assignment assignment) {
        return assignmentController.getAssignmentList(assignment);
    }

    @RequestMapping("/getAssignmentDetail")
    public ResultData getAssignmentListDetail(Assignment assignment) {
        return assignmentController.getAssignmentListDetailTeacher(assignment);
    }

    @RequestMapping("/publishAssignment")
    public ResultData publishAssignment(Assignment assignment) {
        return assignmentController.publishAssignment(assignment);
    }

    @RequestMapping("/gradeAssignment")
    public ResultData gradeAssignment(StudentAssignment studentAssignment){
        return assignmentController.gradeAssignment(studentAssignment);
    }

    @RequestMapping("/deleteAssignment")
    public ResultData deleteAssignment(Assignment assignment) {
        return assignmentController.deleteAssignment(assignment);
    }

    @RequestMapping("/addFile")
    public ResultData addFile(CourseFile courseFile, @RequestParam("file") List<MultipartFile> files, HttpServletRequest req) {
        return fileController.addFile(courseFile,files,req);
    }

    @RequestMapping("/searchFile")
    public ResultData searchFile(CourseFile file) {
        return fileController.searchFile(file);
    }

    @RequestMapping("/downloadFile")
    public ResponseEntity downloadFile(CourseFile file) throws UnsupportedEncodingException {
        return fileController.downloadFile(file);
    }

    @RequestMapping("/modifyFile")
    public ResultData modifyFileInfo(CourseFile file) {
        return fileController.modifyFileInfo(file);
    }

    @RequestMapping("/deleteFile")
    public ResultData deleteFile(Integer fileId) {
        return fileController.deleteFile(fileId);
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
