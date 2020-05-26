package com.java.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Field;
import java.sql.Timestamp;

@Data
@ToString
public class File {
    @TableId(type = IdType.INPUT)
   private Integer fileId;
   private String fileTitle;
   private String fileContent;
   private String fileName;
   private String filePath;
   private Timestamp uploadTime;
   private Integer courseId;
}

