package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@TableName("collection")
public class Collections {
    @TableId(type = IdType.INPUT)
    private Integer collectionId;
    private Integer userId;
    private Integer courseId;
    private Timestamp collectTime;

}
