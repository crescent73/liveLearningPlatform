package com.java.model.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseDetail {
    private Integer courseId;
    private String courseNumber;
    private String courseName;
    private Integer isShow; // 1:show,0:not show
    private String courseDetail;
    private String courseLevel;
    private String courseType;
    private Integer teacherId;
    private String teacherName;
    private String teacherNumber;
}
