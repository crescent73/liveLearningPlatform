package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Course;
import com.java.model.entity.CourseDetail;
import com.java.model.vo.ResultData;
import com.java.service.intf.AdminService;
import com.java.service.intf.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private ResultData resultData;

    @Autowired
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

    @RequestMapping("/modifyCourseInfo")
    public ResultData modifyCourseInfo() {
        return null;
    }


}
