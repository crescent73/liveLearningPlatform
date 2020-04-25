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

}
