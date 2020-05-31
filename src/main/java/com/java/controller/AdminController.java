package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Course;
import com.java.model.entity.CourseDetail;
import com.java.model.vo.ResultData;
import com.java.service.intf.AdminService;
import com.java.service.intf.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    private ResultData resultData;

    private CourseService courseService;

    @RequestMapping("/courseList")
    public ResultData courseList(Course course) {
        ResultData <List <Course>> resultDta =new ResultData <List <Course>>();
        if(course==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<CourseDetail> courseList = courseService.getCourse(course);
        if (courseList!=null){
            resultData.setData(courseList);
            resultData.setResult(ResultCodeEnum.OK);
        } else {
            resultData.setResult(ResultCodeEnum.DB_FIND_FAILURE);
        }
        return resultData;
    }

    @RequestMapping("/addCourse")
    @ResponseBody
    public ResultData addCourse(Course course) {
        System.out.println(course);
        if(StringUtils.isNotBlank(course.getCourseName()) && StringUtils.isNotBlank(course.getCourseNumber())
                && StringUtils.isNotBlank(course.getCourseSemester()) && course.getTeacherId() != null
                && course.getCourseType() != null && course.getCourseCredit() != null
                && course.getCourseSchool() != null) {
            //调用CourseService
            try{
                resultData = adminService.addCourse(course);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
        } else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);//必要请求参数为空
        }
        System.out.println(resultData);
        return resultData;
    }

    @RequestMapping("/modifyCourseInfo")
    public ResultData modifyCourseInfo() {
        return null;
    }

    @RequestMapping("/deleteCourse")
    public ResultData deleteCourse(Integer courseId) {
        System.out.println(courseId);
        if(courseId!=null) {
            //调用删除课程service
            try{
                resultData = adminService.deleteCourse(courseId);
            }catch(Exception e){
                e.printStackTrace();
                resultData = new ResultData();
                resultData.setResult(ResultCodeEnum.SERVER_ERROR);
            }
        } else {
            resultData = new ResultData();
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
        }
        return resultData;
    }

}
