package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Sign {
    @TableId(type = IdType.AUTO)
    private Integer signId;
    private Integer courseId;
    private Integer userId;
    private Integer courseScheduleId;
    private Timestamp signTime;
}
