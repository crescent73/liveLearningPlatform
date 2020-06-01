package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseDetail extends Course {
    private String teacherName;
    private String teacherNumber;
}
