package com.java.model.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class Video {
    private Integer videoId;
    private String videoName;
    private String videoContent;
    private Timestamp uploadTime;
    private String videoPath;
    private Integer courseId;
    private Integer courseScheduleId;
}
