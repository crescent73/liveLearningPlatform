package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentCourse {
    private Integer studentCourseId;
    private Integer courseId;
    private Integer userId;
    private Double courseScore;
}
