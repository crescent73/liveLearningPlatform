package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.dto.SignDetail;
import com.java.model.entity.*;
import com.java.model.entity.Collections;
import com.java.model.vo.ResultData;
import com.java.service.intf.CollectionService;
import com.java.service.intf.CourseService;
import com.java.service.intf.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/teacher")
public class TeacherController {


    private SignService signService;
    private CourseService courseService;
    private CollectionService collectionService;

    @Autowired
    public void setSignService(SignService signService) {
        this.signService = signService;
    }
    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @RequestMapping("/searchCourse")
    public ResultData getCourseList(Course course) {
        ResultData<List<Course>> resultData = new ResultData<>();
        if (course==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Course> courseList = courseService.getCourseList(course);
        if (courseList!=null){
            resultData.setData(courseList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/addCourse")
    public ResultData createCourse(Course course) {
        ResultData resultData = new ResultData();
        if (course.getCourseName()==null
                ||course.getCourseNumber()==null
                ||course.getTeacherId()==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = courseService.createCourse(course);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if(result == -2){
            resultData.setResult(ResultCodeEnum.DB_ADD_TEACHER_NOT_EXIST);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/deleteCourse")
    public ResultData deleteCourse(Integer[] courseList) {
        ResultData resultData = new ResultData();
        if (courseList==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Integer> courseIdList = Arrays.asList(courseList);
        int result = courseService.deleteCourse(courseIdList);
        if (result>= courseList.length){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/startCourse")
    public ResultData startCourse(Integer courseId) {
        ResultData<Map> resultData = new ResultData<>();
        if (courseId==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        CourseSchedule courseSchedule = new CourseSchedule();
        courseSchedule.setCourseId(courseId);
        int courseScheduleId = courseService.startCourse(courseSchedule);
        if (courseScheduleId>0){
            resultData.setResult(ResultCodeEnum.OK);
            Map<String,Integer> map = new HashMap<>();
            map.put("courseScheduleId",courseScheduleId);
            resultData.setData(map);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/endCourse")
    public ResultData endCourse(Integer courseScheduleId) {
        ResultData resultData = new ResultData();
        if (courseScheduleId==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        CourseSchedule courseSchedule = new CourseSchedule();
        courseSchedule.setCourseScheduleId(courseScheduleId);
        int result = courseService.endCourse(courseSchedule);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/setCoursePermission")
    public ResultData setCoursePermission(Integer id,Integer courseType) {
        ResultData resultData = new ResultData();
        if (id ==null || courseType == null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        Course course = new Course();
        course.setCourseId(id);
        course.setIsShow(courseType);
        int result = courseService.setCoursePermission(course);
        if (result >0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    @RequestMapping("/getCollectionCourseList")
    public ResultData collectCourseList(Course course,Integer userId) {
        ResultData<List<Course>> resultData = new ResultData<>();
        if (userId == null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Course> courseList = collectionService.collectCourseList(course, userId);
        if (courseList!=null){
            resultData.setData(courseList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/addCollectCourse")
    public ResultData addCollectCourse(Integer courseId,Integer userId) {
        ResultData resultData = new ResultData();
        // 检查参数
        if (courseId==null||userId==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 调用service
        Collections collections = new Collections();
        collections.setCourseId(courseId);
        collections.setUserId(userId);
        int result = collectionService.addCollectCourse(collections);
        // 封装返回结果
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -2){
            resultData.setResult(ResultCodeEnum.TEACHER_NOT_EXIST);
        } else if (result == -3){
            resultData.setResult(ResultCodeEnum.COURSE_NOT_EXIST);
        } else{
            resultData.setResult(ResultCodeEnum.DB_SIGN_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/deleteCollectCourse")
    public ResultData deleteCollectCourse(Integer[] collectionId) {
        ResultData resultData = new ResultData();
        if (collectionId==null||collectionId.length<=0) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Integer> collectionIdList = Arrays.asList(collectionId);
        int result = collectionService.deleteCollectCourse(collectionIdList);
        if (result >= collectionIdList.size()){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
        }
        return resultData;
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
    public ResultData sendLivingStream(Integer courseScheduleId,Integer teacherId) {
        ResultData<Map> resultData = new ResultData<>();
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
        ResultData resultData = new ResultData();
        // 非空判断
        if (courseId == null || teacherId == null || courseScheduleId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 传入参数
        Sign sign = new Sign();
        sign.setCourseId(courseId);
        sign.setUserId(teacherId);
        sign.setCourseScheduleId(courseScheduleId);
        int result = signService.publishSign(sign);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -2){
            resultData.setResult(ResultCodeEnum.COURSE_NOT_EXIST);
        } else if (result == -3){
            resultData.setResult(ResultCodeEnum.STUDENT_NOT_EXIST);
        } else if (result == -4){
            resultData.setResult(ResultCodeEnum.COURSE_SCHEDULE_NOT_EXIST);
        } else{
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/getSignList")
    public ResultData getSignList(Integer courseId,Integer teacherId,Integer courseScheduleId) {
        ResultData<List<Sign>> resultData = new ResultData<>();
        // 非空判断
        if (courseId == null||teacherId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 传入参数
        Sign sign = new Sign();
        sign.setCourseId(courseId);
        sign.setUserId(teacherId);
        if (courseScheduleId!=null){
            sign.setCourseScheduleId(courseScheduleId);
        }
        List<Sign> signDetail = signService.getSignList(sign);
        if (signDetail!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(signDetail);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/getSignDetail")
    public ResultData getSignDetail(Integer signId) {
        ResultData<List<SignDetail>> resultData = new ResultData<>();
        // 非空判断
        if (signId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        // 传入参数
        SignStudent signStudent = new SignStudent();
        signStudent.setSignId(signId);
        List<SignDetail> signDetail = signService.getSignDetail(signStudent);
        if (signDetail!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(signDetail);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/getNoticeList")
    public ResultData getNoticeList() {
        return null;
    }

    @RequestMapping("/addNotice")
    public ResultData publishNotice() {
        return null;
    }

    @RequestMapping("/modifyNotice")
    public ResultData modifyNotice() {
        return null;
    }

    @RequestMapping("/deleteNotice")
    public ResultData deleteNotice() {
        return null;
    }

    @RequestMapping("/getAssignmentList")
    public ResultData getAssignmentList() {
        return null;
    }

    @RequestMapping("/getAssignmentDetail")
    public ResultData getAssignmentDetail() {
        return null;
    }

    @RequestMapping("/publishAssignment")
    public ResultData publishAssignment() {
        return null;
    }

    @RequestMapping("/gradeAssignment")
    public ResultData gradeAssignment() {
        return null;
    }

    @RequestMapping("/deleteAssignment")
    public ResultData deleteAssignment() {
        return null;
    }

    @RequestMapping("/addFile")
    public ResultData uploadFile() {
        return null;
    }

    @RequestMapping("/searchFile")
    public ResultData getFileList() {
        return null;
    }

    @RequestMapping("/downloadFile")
    public ResultData downloadFile() {
        return null;
    }

    @RequestMapping("/modifyFile")
    public ResultData modifyFileInfo() {
        return null;
    }

    @RequestMapping("/deleteFile")
    public ResultData deleteFile() {
        return null;
    }
}
