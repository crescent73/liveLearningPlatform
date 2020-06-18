package com.java.model.dto;

import com.java.model.entity.Course;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class CollectionDetail extends CourseDetail {
    private Integer collectionId;
    private Integer userId;
    private Timestamp collectTime;
}
