package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;
import java.sql.Timestamp;

@Data
@ToString
public class Notice {
    @TableId(type = IdType.INPUT)
    private Integer noticeId;
    private String noticeTitle;
    private String noticeContent;
    private Timestamp publishTime;
    private Integer courseId;
    private Integer userId;
}
