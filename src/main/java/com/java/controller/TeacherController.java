package com.java.controller;

import com.java.constant.enums.FileStorage;
import com.java.constant.enums.ResultCodeEnum;
import com.java.model.dto.SignDetail;
import com.java.model.entity.*;
import com.java.model.vo.ResultData;
import com.java.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/teacher")
public class TeacherController {

    private CourseService courseService;
    private CollectionService collectionService;
    private SignService signService;
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
    public ResultData getCourseList(Course course) {
        ResultData <List<Course>> resultData = new ResultData <List<Course>>();
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
            resultData.setResult(ResultCodeEnum.TEACHER_NOT_EXIST);
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
        ResultData <Map> resultData = new ResultData <>();
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
    public ResultData setCoursePermission(Integer id, Integer courseType) {
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
    public ResultData getSignList(Integer courseId, Integer teacherId, Integer courseScheduleId) {
        ResultData <List<Sign>> resultData = new ResultData <>();
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
        ResultData <List<SignDetail>> resultData = new ResultData <>();
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


    @RequestMapping("/addNotice")
    public ResultData addNotice(Notice notice) {
        ResultData<Notice> resultData = new ResultData <>();
        if (notice.getCourseId() == null || notice.getUserId() == null
                ||notice.getNoticeTitle() == null || notice.getNoticeContent() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = noticeService.publishNotice(notice);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/modifyNotice")
    public ResultData modifyNotice(Notice notice) {
        ResultData<Notice> resultData = new ResultData <>();
        if (notice.getNoticeId() == null || ( notice.getUserId() == null && notice.getCourseId() == null
                && notice.getNoticeTitle() == null && notice.getNoticeContent() == null)) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = noticeService.modifyNotice(notice);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    @RequestMapping("/deleteNotice")
    public ResultData deleteNotice(Notice notice) {
        ResultData<Notice> resultData = new ResultData <>();
        if (notice.getNoticeId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = noticeService.deleteNotice(notice);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
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

    @RequestMapping("/getAssignmentDetail")
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


    @RequestMapping("/publishAssignment")
    public ResultData publishAssignment(Assignment assignment) {
        ResultData<Assignment> resultData = new ResultData <>();
        if (assignment.getCourseId() == null || assignment.getUserId() == null
                ||assignment.getAssignmentTitle() == null || assignment.getAssignmentContent() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.publishAssignment(assignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/gradeAssignment")
    public ResultData gradeAssignment(StudentAssignment studentAssignment){
        ResultData<StudentAssignment> resultData = new ResultData <>();
        if (studentAssignment.getStudentAssignmentId() == null || studentAssignment.getScore() == null
                || studentAssignment.getTeacherReply() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.gradeAssignment(studentAssignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    @RequestMapping("/deleteAssignment")
    public ResultData deleteAssignment(Assignment assignment) {
        ResultData<Assignment> resultData = new ResultData <>();
        if (assignment.getAssignmentId()==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = assignmentService.deleteAssignment(assignment);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_DELETE_FAILURE);
        }
        return resultData;
    }


    @RequestMapping("/addFile")
    public ResultData addFile(CourseFile courseFile, @RequestParam("file") List<MultipartFile> files, HttpServletRequest req) {
        ResultData resultData = new ResultData <>();
        int result;
        if (courseFile.getCourseId()==null || courseFile.getUserId()==null || courseFile.getFileTitle() == null || files == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        for (MultipartFile file : files) {
            courseFile.setFileName(file.getOriginalFilename());
            String dirPath = req.getServletContext().getRealPath(FileStorage.FILE_STORAGE_PATH);
            System.out.println(dirPath);
            courseFile.setFilePath(dirPath);
            System.out.println(courseFile);
            result = fileService.uploadFile(courseFile,file);
            if (result <0 ) {
                resultData.setResult(ResultCodeEnum.FILE_UPLOAD_FAILURE);
            }
        }
        resultData.setResult(ResultCodeEnum.FILE_UPLOAD_SUCCESS);
        return resultData;
    }

    @RequestMapping("/searchFile")
    public ResultData searchFile(CourseFile file) {
        ResultData<List<CourseFile>> resultData = new ResultData <>();
        List<CourseFile> fileList = fileService.getFileList(file);
        if (fileList.size()>0) {
            resultData.setResult(ResultCodeEnum.OK);
            resultData.setData(fileList);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/downloadFile")
    public ResponseEntity downloadFile(CourseFile file) throws UnsupportedEncodingException {
        ResultData<CourseFile> resultData = new ResultData <>();
        if (file.getFileId() == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return ResponseEntity.ok().body(resultData);
        }
        File downloadFile = fileService.downloadFile(file);
        if (downloadFile == null) {
            resultData.setResult(ResultCodeEnum.FILE_EMPTY); //文件下载失败
            return ResponseEntity.ok().body(resultData);
        }
        HttpHeaders headers = new HttpHeaders();
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(downloadFile));
        } catch (IOException e) {
            e.printStackTrace();
            resultData.setResult(ResultCodeEnum.FILE_DOWNLOAD_FAILURE); //文件下载失败
            return ResponseEntity.ok().body(resultData);
        }
        List<CourseFile> fileList = fileService.getFileList(file);
        CourseFile courseFile = fileList.get(0);
        String encodedFileName = URLEncoder.encode(courseFile.getFileName(),"utf-8");
//        System.out.println(encodedFileName);
        headers.setContentDispositionFormData("file",encodedFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @RequestMapping("/modifyFile")
    public ResultData modifyFileInfo(CourseFile file) {
        ResultData resultData = new ResultData <>();
        if (file.getFileId() == null || (file.getFileTitle()== null && file.getFileContent() == null)) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = fileService.modifyFileInfo(file);
        if (result>0) {
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_UPDATE_ERROR);
        }
        return resultData;
    }

    @RequestMapping("/deleteFile")
    public ResultData deleteFile(Integer fileId) {
        ResultData resultData = new ResultData <>();
        System.out.println(fileId);
        if (fileId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        int result = fileService.deleteFile(fileId);
        if(result>0) {
            resultData.setResult(ResultCodeEnum.OK);
        } else if (result == -1) {
            resultData.setResult(ResultCodeEnum.FILE_EMPTY);
        } else {
            resultData.setResult(ResultCodeEnum.FILE_DELETE_FAILURE);
        }
        return resultData;
    }
}
