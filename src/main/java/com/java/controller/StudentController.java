package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.*;
import com.java.model.vo.ResultData;
import com.java.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private SignService signService;
    private CourseService courseService;
    private CollectionService collectionService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private FileService fileService;

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
    public ResultData getCourseList(Course course, Integer studentId) {
        ResultData <List<Course>> resultData = new ResultData <List<Course>>();
        if (studentId==null||course==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Course> courseList = courseService.getStudentCourse(studentId,course);
        if (courseList!=null){
            resultData.setData(courseList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/getCollectionCourseList")
    public ResultData collectCourseList(Course course, Integer userId) {
        ResultData <List<Course>> resultData = new ResultData <>();
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
    public ResultData addCollectCourse(Integer courseId, Integer userId) {
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
            resultData.setResult(ResultCodeEnum.STUDENT_NOT_EXIST);
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
        ResultData resultData = new ResultData();
        if (signId==null||studentId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        SignStudent signStudent = new SignStudent();
        signStudent.setSignId(studentId);
        signStudent.setSignId(signId);
        int result = signService.sign(signStudent);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -2){
            resultData.setResult(ResultCodeEnum.SIGN_NOT_EXIST);
        } else if (result == -3){
            resultData.setResult(ResultCodeEnum.STUDENT_NOT_EXIST);
        } else{
            resultData.setResult(ResultCodeEnum.DB_SIGN_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/getNoticeList")
    public ResultData getNoticeList(Notice notice) {
        ResultData <List<Notice>> resultData = new ResultData <>();
        if (notice.getCourseId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Notice> noticeList = noticeService.getNoticeList(notice);
        if (noticeList!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(noticeList);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }


    @RequestMapping("/getAssignmentList")
    public ResultData getAssignmentList(Assignment assignment) {
        ResultData<List<Assignment>> resultData = new ResultData <>();
        if (assignment.getCourseId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<Assignment> assignmentList = assignmentService.getAssignmentList(assignment);
        if (assignmentList!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(assignmentList);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/getAssignmentListDetail")
    public ResultData getAssignmentListDetail(Assignment assignment) {
        ResultData<Assignment> resultData = new ResultData <>();
        if (assignment.getAssignmentId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        Assignment assignmentDetail = assignmentService.getAssignmentDetail(assignment);
        if (assignmentDetail!=null){
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(assignmentDetail);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/submitAssignment")
    public ResultData submitAssignment(StudentAssignment studentAssignment){
        ResultData<StudentAssignment> resultData = new ResultData <>();
        if (studentAssignment.getUserId() == null || studentAssignment.getAssignmentId() == null
                ||studentAssignment.getAssignmentSubmission() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.submitAssignment(studentAssignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }


    @RequestMapping("/searchFile")
    public ResultData searchFile(File file) {
        return null;
    }

    @RequestMapping("/downloadFile")
    public ResultData downloadFile(File file) {
        return null;
    }

}
