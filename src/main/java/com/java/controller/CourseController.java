package com.java.controller;

import com.java.constant.enums.FileStorage;
import com.java.constant.enums.ResultCodeEnum;
import com.java.model.dto.CourseDetail;
import com.java.model.entity.Course;
import com.java.model.entity.CourseFile;
import com.java.model.entity.CourseSchedule;
import com.java.model.vo.ResultData;
import com.java.service.intf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseController {
    @Autowired
    private CourseService courseService;

    public ResultData getCourseList(Course course) {
        ResultData <List<CourseDetail>> resultData = new ResultData <>();
        List<CourseDetail> courseList = courseService.getCourseList(course);
        if (courseList!=null){
            resultData.setData(courseList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    public ResultData getStudentCourseList(Integer studentId, Course course) {
        ResultData <List<CourseDetail>> resultData = new ResultData <>();
        if (studentId==null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<CourseDetail> courseList = courseService.getStudentCourse(studentId,course);
        if (courseList!=null){
            resultData.setData(courseList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    public ResultData createCourse(Course course, MultipartFile picture, HttpServletRequest req) {
        ResultData resultData = new ResultData();
        if (course.getCourseName()==null
                ||course.getCourseNumber()==null
                ||course.getTeacherId()==null || picture == null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        course.setCoursePictureName(picture.getOriginalFilename());
        String dirPath = req.getServletContext().getRealPath(FileStorage.PICTURE_STORAGE_PATH);
        System.out.println(dirPath);
        course.setCoursePicturePath(dirPath);
        int result = courseService.createCourse(course,picture);
        if (result>0){
            resultData.setResult(ResultCodeEnum.OK);
        } else if(result == -2){
            resultData.setResult(ResultCodeEnum.TEACHER_NOT_EXIST);
        } else {
            resultData.setResult(ResultCodeEnum.DB_ADD_FAILURE);
        }
        return resultData;
    }

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

    public byte[] getImage(Integer courseId){
        if (courseId==null) {
            return null;
        }
        Course course = courseService.getCourseById(courseId);
        if (course !=null) {
            System.out.println(course);
            try (InputStream is = new FileInputStream(course.getCoursePicturePath())){
                byte[] bytes = new byte[is.available()];
                is.read(bytes,0,is.available());
                return bytes;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public ResponseEntity getCourseImage(Integer courseId) throws UnsupportedEncodingException {
        ResultData<CourseFile> resultData = new ResultData <>();
        if (courseId == null) {
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return ResponseEntity.ok().body(resultData);
        }
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            resultData.setResult(ResultCodeEnum.FILE_EMPTY); //文件下载失败
            return ResponseEntity.ok().body(resultData);
        }
        HttpHeaders headers = new HttpHeaders();
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(new File(course.getCoursePicturePath())));
        } catch (IOException e) {
            e.printStackTrace();
            resultData.setResult(ResultCodeEnum.FILE_DOWNLOAD_FAILURE); //文件下载失败
            return ResponseEntity.ok().body(resultData);
        }
        String encodedFileName = URLEncoder.encode(course.getCoursePictureName(),"utf-8");
//        System.out.println(encodedFileName);
//        headers.setContentDispositionFormData("file",encodedFileName);
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
