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
    private Integer courseScheduleId;
    private Integer courseId;
    private Timestamp startTime;
    private Timestamp endTime;
}
