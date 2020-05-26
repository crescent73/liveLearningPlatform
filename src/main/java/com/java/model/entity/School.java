package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class School {
    @TableId(type = IdType.INPUT)
    private Integer schoolId;
    private String schoolNumber;
    private String schoolName;
}
