package com.java.controller;

import com.java.constant.enums.FileStorage;
import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Collections;
import com.java.model.entity.Course;
import com.java.model.entity.File;
import com.java.model.entity.SignStudent;
import com.java.model.vo.ResultData;
import com.java.service.intf.CollectionService;
import com.java.service.intf.CourseService;
import com.java.service.intf.SignService;
import com.java.service.intf.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    private StudentService studentService;
    private ResultData resultData;
    public StudentController() {
        resultData = new ResultData();
    }

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

    @RequestMapping("/submitAssignment")
    public ResultData submitAssignment(Integer courseId, Long uploaderId, String fileName , String fileDescription,
                                       @RequestParam("file") List<MultipartFile> files, HttpServletRequest req) {
        System.out.println("courseId:"+courseId+",uploaderId:"+uploaderId+",fileName:"+fileName);
        if(courseId != null && uploaderId != null && StringUtils.isNotBlank(fileName)){
            if(files != null && files.size() > 0){
                File file = new File();
                file.setCourseId(courseId);
                file.setUploaderId(Math.toIntExact(uploaderId));
                file.setFileName(fileName);
                if(fileDescription != null)
                    file.setFileDescription(fileDescription);
                String dirPath = req.getServletContext().getRealPath(FileStorage.FILE_STORAGE_PATH);
                try{
                    resultData = studentService.submitAssignment(file,dirPath,files);
                }catch(Exception e){
                    e.printStackTrace();
                    resultData = new ResultData();
                    resultData.setResult(ResultCodeEnum.SERVER_ERROR);
                }
            } else{
                resultData.setResult(ResultCodeEnum.FILE_UPLOAD_EMPTY);  //上传附件为空
            }
        }else {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);  //重要参数为空
        }

        return resultData;
    }

}
