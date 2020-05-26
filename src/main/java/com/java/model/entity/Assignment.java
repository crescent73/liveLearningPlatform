package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Assignment {
    @TableId(type = IdType.INPUT)
    private Integer assignmentId;
    private String assignmentTitle;
    private String assignmentContent;
    private Timestamp publishTime;
    private Integer courseId;
    private Integer courseScheduleId;
    private Integer userId;
}
