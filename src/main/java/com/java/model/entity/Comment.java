package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Comment {
    @TableId(type = IdType.INPUT)
    private Integer commentId;
    private Integer courseId;
    private Integer userId;
    private String commentContent;
    private Timestamp publishTime;
}
