package com.java.controller;

import com.java.model.entity.Course;
import com.java.model.entity.Student;
import com.java.model.vo.ResultData;
import com.java.service.intf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    public ResultData getCourseList(Course course) {
        return null;
    }

    public ResultData getCourseDetail(Course course) {
        return null;
    }

    public ResultData collectCourseList() {
        return null;
    }

    public ResultData addCollectCourse() {
        return null;
    }

    public ResultData deleteCollectCourse() {
        return null;
    }

    public ResultData getVideo() {
        return null;
    }

    public ResultData deleteVideo() {
        return null;
    }

    public ResultData getLivingStream() {
        return null;
    }

    public ResultData sign() {
        return null;
    }

    public ResultData getNoticeList() {
        return null;
    }

    public ResultData getAssignmentList() {
        return null;
    }

    public ResultData getAssignmentDetail() {
        return null;
    }

    public ResultData submitAssignment() {
        return null;
    }

    public ResultData getFileList() {
        return null;
    }

    public ResultData downloadFile() {
        return null;
    }
}
