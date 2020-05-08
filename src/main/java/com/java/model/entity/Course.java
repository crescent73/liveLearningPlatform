package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Course {
    @TableId(type = IdType.INPUT)
    private Integer courseId;
    private String courseNumber;
    private String courseName;
    private Integer isShow; // 1:show,0:not show
    private Integer teacherId; // user_role=2
    private Integer courseSchool;
    private String courseSemester;
    private Integer courseType;
    private String courseDescription;
    private String courseTeacherDescription;
    private Float courseCredit;

    public Integer getId() {
        return courseId;
    }
    public void setId(Integer courseId) {
        this.courseId = courseId;
    }



}
