package com.java.controller;

import com.java.constant.enums.ResultCodeEnum;
import com.java.model.entity.Course;
import com.java.model.dto.CourseDetail;
import com.java.model.vo.ResultData;
import com.java.service.intf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
        ResultData <List <CourseDetail>> resultDta =new ResultData <>();
        if(course==null){
            resultData.setResult(ResultCodeEnum.PARA_WORNING_NULL);
            return resultData;
        }
        List<CourseDetail> courseList = courseService.getCourseList(course);
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
