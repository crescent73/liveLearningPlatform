package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class CourseSchedule{
    @TableId(type = IdType.INPUT)
    private Integer id;
    private Integer courseScheduleId;
    private Integer courseId;
    private Timestamp startTime;
    private Timestamp endTime;
    private   Integer studentId;

    public CourseSchedule() {
    }

    public CourseSchedule(Integer id) {
        this.id = id;
    }

    public CourseSchedule(Integer studentId, Integer courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
}
