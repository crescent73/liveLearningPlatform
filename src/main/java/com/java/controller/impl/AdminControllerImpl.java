package com.java.controller.impl;

import com.java.controller.intf.AdminController;
import com.java.model.entity.Course;
import com.java.model.vo.ResultData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {

    @RequestMapping("/courseList")
    public ResultData courseList() {
        return null;
    }

    @RequestMapping("/courseDetail")
    public ResultData courseDetail() {
        return null;
    }

    @RequestMapping("/modifyCourseInfo")
    public ResultData modifyCourseInfo() {
        return null;
    }

    @RequestMapping("/deleteCourse")
    public ResultData deleteCourse() {
        return null;
    }

}
